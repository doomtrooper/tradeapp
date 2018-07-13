import org.junit.Assert;

public class Test {
    @org.junit.Test
    public void testRegex(){
        String str = "234| IBM |MS | Morgan Stanley |300 | 05/15/2018 | 05/12/2018|B";
        String[] split = str.split("[|][\\s]*");
        Assert.assertTrue(split.length==8);
    }
}
