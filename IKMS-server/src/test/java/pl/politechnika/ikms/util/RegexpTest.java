package pl.politechnika.ikms.util;

import org.hamcrest.Matchers;
import org.junit.Test;
import pl.politechnika.ikms.commons.util.CommonConstants;

import java.time.LocalDate;
import java.util.regex.Pattern;

import static org.junit.Assert.assertThat;

public class RegexpTest {

    @Test
    public void emailRegexp(){
        String properEmail = "asdasdsadasd@gmail.pl";
        String invalidEmail = "faosnasoóóóó2323jasdasd";

        Pattern ptrn = Pattern.compile(CommonConstants.EMAIL_REGEXP);
        boolean matchesValid = ptrn.matcher(properEmail).matches();
        boolean matchesInvalid = ptrn.matcher(invalidEmail).matches();

        assertThat(matchesValid, Matchers.is(true));
        assertThat(matchesInvalid, Matchers.is(false));
    }

    @Test
    public void localDateTest(){
        LocalDate date = LocalDate.of(2017,11,11);
        System.out.println(date);

    }

}
