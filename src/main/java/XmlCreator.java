import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by 430 on 13.01.2018.
 */
public class XmlCreator {
    private XMLOutputFactory outputFactory;
    private XMLStreamWriter xmlStreamWriter;
    private List<Offer> offers;

    public XmlCreator(List<Offer> offers) {
        this.outputFactory = XMLOutputFactory.newInstance();
        this.offers = offers;
    }

    public void writeToXml() throws IOException {
        File outFile = new File("D://offers.xml"); //output file in xml
        FileOutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(outFile);
            xmlStreamWriter = outputFactory.createXMLStreamWriter(outputStream, "UTF-8");

            // create the xml template
            xmlStreamWriter.writeStartDocument("UTF-8","1.0");
            xmlStreamWriter.writeStartElement("offers");

            // write offer
            for (int i = 0; i < offers.size(); i++) {
                writeOfferToXml(offers.get(i)); // write price
            }

            xmlStreamWriter.writeEndElement();
            xmlStreamWriter.writeEndDocument();

            xmlStreamWriter.flush();
            xmlStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                outputStream.close();
            }
        }
    }

    private void writeOfferToXml(Offer offer) {
        try {
            xmlStreamWriter.writeStartElement("offer");

            // write product name
            xmlStreamWriter.writeStartElement("name");
            xmlStreamWriter.writeCharacters(offer.getName());
            xmlStreamWriter.writeEndElement();

            // write brand
            xmlStreamWriter.writeStartElement("brand");
            xmlStreamWriter.writeCharacters(offer.getBrand());
            xmlStreamWriter.writeEndElement();

            // write color
            xmlStreamWriter.writeStartElement("color");
            xmlStreamWriter.writeCharacters(offer.getColor());
            xmlStreamWriter.writeEndElement();

            // write price
            xmlStreamWriter.writeStartElement("price");
            xmlStreamWriter.writeCharacters(offer.getPrice());
            xmlStreamWriter.writeEndElement();

            // write initial price
            xmlStreamWriter.writeStartElement("initialPrice");
            xmlStreamWriter.writeCharacters(offer.getInitialPrice());
            xmlStreamWriter.writeEndElement();

            // write description
            xmlStreamWriter.writeStartElement("description");
            xmlStreamWriter.writeCharacters(offer.getDescription());
            xmlStreamWriter.writeEndElement();

            // write article id
            xmlStreamWriter.writeStartElement("articleID");
            xmlStreamWriter.writeCharacters(offer.getArticleId());
            xmlStreamWriter.writeEndElement();

            // write cost
            xmlStreamWriter.writeStartElement("shippingCosts");
            xmlStreamWriter.writeCharacters(offer.getShippingCosts());
            xmlStreamWriter.writeEndElement();

            xmlStreamWriter.writeEndElement();
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
    }
}
