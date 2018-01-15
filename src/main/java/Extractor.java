import org.jsoup.Jsoup;
import org.jsoup.helper.Validate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by 430 on 12.01.2018.
 */
public class Extractor {
    private HashSet<String> links;
    private static List<Offer> offers;
    Summary summary;

    public Extractor() {
        links = new HashSet<>();
        offers = new ArrayList<>();
    }

    public static void main(String[] args) {
        Validate.isTrue(args.length == 1, "usage: supply url to fetch");
        String keyword = args[0]; // socken
        String URL = "https://www.aboutyou.de";

        Extractor extractor = new Extractor();
        Summary summary = new Summary();

        // start of the program
        summary.setCurrentTime("start", System.currentTimeMillis());

        // get offers by keyword
        print("Searching pages for keyword: %s", keyword);
        extractor.getPageLinks(URL, keyword);
        print("Links: (%d)", extractor.links.size());

        //compile offers
        print("Compiling offers: %s", "");
        extractor.fillOutOffers(); // fill offer with properties

        // transmit list of offers to summary
        summary.setOffers(extractor.getOffers());
        // save to xml file
        extractor.saveToXml();

        // end of program
        summary.setCurrentTime("end", System.currentTimeMillis());
    }
    
    public List<Offer> getOffers() {
        return offers;
    }

    public void getPageLinks(String URL, String keyword) {
        if (!links.contains(URL)) {
            try {
                Document document = Jsoup.connect(URL).get();
                Elements otherLinks = document.getAllElements()
                        .select("a[href*=" + keyword + "]");

                for (Element page: otherLinks) {
                    if (links.add(URL)) {
                        print(URL);
                    }
                    getPageLinks(page.attr("abs:href"), keyword);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void fillOutOffers() {
        links.forEach(x -> {
            try {
                Document document = Jsoup.connect(x).get();
                Elements elements = document.select("div[class*=category]");

                for (Element e: elements) {
                    offers.add(getOfferWithProperties(e)); // add completed offer to list
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private Offer getOfferWithProperties(Element e) {

        String brand = e.select("[class*=brand]").text(); // get brand
        String name = e.select("p[class*=name]").text(); // get product name
        String description = brand + "|" + name; // get description by concatenation name and brand
        String price = e.select("div[class^=finalPrice]").text(); // get current (final) product price
        String initialPrice = e.select("ul:has(li)")
                .select("[class^=price]").text(); // get current (final) product price

        Offer offer = new Offer();
        offer.setName(name); // set name to offer
        offer.setBrand(brand); // set brand to offer
        offer.setDescription(description); // set description to offer
        offer.setPrice(price); // set price(final) to offer
        offer.setInitialPrice(initialPrice); // set initial price to offer

        offers.add(offer); // add to list offer
        print("Name: %s\nBrand: %s\nDescription: %s\nPrice: %s\nInitialPrice: %s",
                name, brand, description, price, initialPrice);
        return offer; //return offer with searched properties
    }
    private void saveToXml() {
        XmlCreator xmlCreator = new XmlCreator(offers);
        try {
            xmlCreator.writeToXml();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void print(String msg, Object... args) {
        System.out.println(String.format(msg, args));
    }
}
