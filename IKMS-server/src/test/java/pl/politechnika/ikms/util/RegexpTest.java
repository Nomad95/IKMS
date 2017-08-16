package pl.politechnika.ikms.util;

import org.hamcrest.Matchers;
import org.junit.Test;
import pl.politechnika.ikms.commons.util.CommonConstants;
import pl.politechnika.ikms.domain.user.enums.Roles;

import java.util.regex.Pattern;

import static org.junit.Assert.*;

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

}
