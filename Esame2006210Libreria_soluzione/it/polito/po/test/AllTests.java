package it.polito.po.test;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

    public static Test suite() {
        TestSuite suite = new TestSuite("Test for esame libreria");
        //$JUnit-BEGIN$
        suite.addTestSuite(TestR1_Editori.class);
        suite.addTestSuite(TestR2_Libri.class);
        suite.addTestSuite(TestR3_Vendita.class);
        suite.addTestSuite(TestR4_Ordini.class);
        suite.addTestSuite(TestR5_LetturaFile.class);
        //$JUnit-END$
        return suite;
    }
}
