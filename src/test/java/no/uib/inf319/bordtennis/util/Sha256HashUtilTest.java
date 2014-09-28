package no.uib.inf319.bordtennis.util;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class Sha256HashUtilTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public final void hashOfDanielLokshtanovShouldBeCorrect() {
        String string = "Daniel Lokshtanov";
        String hash = "833add45b20288e613c821e95478f5d9"
                + "a972059ecb5cc14a55e4a32ab324fa43";

        assertEquals(hash, Sha256HashUtil.sha256hash(string));
    }

    @Test
    public final void hashOfKjetilLundShouldBeCorrect() {
        String string = "Kjetil Lund";
        String hash = "262bef771ae23d287619ab80f88e6133"
                + "235535452575d4d59affc9e00dabf779";

        assertEquals(hash, Sha256HashUtil.sha256hash(string));
    }

    @Test
    public final void hashBeginingWithZerosShouldHaveZerosInString()
                throws Exception {
        String string = "Hello, world!4250";
        String hash = "0000c3af42fc31103f1fdc0151fa747f"
                + "f87349a4714df7cc52ea464e12dcd4e9";

        assertEquals(hash, Sha256HashUtil.sha256hash(string));
    }
}
