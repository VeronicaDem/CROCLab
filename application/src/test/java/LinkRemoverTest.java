import InputFile.InputFile;
import ProcessingServices.LinksService.LinksRemover;
import org.apache.poi.hslf.model.textproperties.TextPFException9;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;

public class LinkRemoverTest {

    @Test
    public void urlType1Link(){
        String textWithLink = "http://site/ssylki Нашиhttp://site/ssylki онлайн-сервисы работают, чтобы сэкономить ваше время и ресурсы.";
        String actualProcessedText = LinksRemover.removeLinks(textWithLink);

        String expectedProcessedText = " Наши онлайн-сервисы работают, чтобы сэкономить ваше время и ресурсы.";

        Assert.assertEquals(expectedProcessedText, actualProcessedText);
    }

    @Test
    public void urlType2Link(){
        String textWithLink = "https://ru.wikipedia.org/wiki/Википедия Наши онлайн-сервисы работают, " +
                "чтобы сэкономить ваше время и ресурсы.";
        String actualProcessedText = LinksRemover.removeLinks(textWithLink);

        String expectedProcessedText = "  Наши онлайн-сервисы работают, чтобы сэкономить ваше время и ресурсы.";

        Assert.assertEquals(expectedProcessedText, actualProcessedText);
    }
}
