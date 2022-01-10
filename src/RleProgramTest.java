import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class RleProgramTest
{
    @Test
    public void countRunsTest()
    {
        byte[] flatData = {1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5,1,2,3,4,5};
        int groups = 25; //expected result
        assertEquals(groups, RleProgram.countRuns(flatData));
    }

    @Test
    public void encodeRleTest()
    {
        byte[] flatData = {4,4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}; // input
        byte[] rleData = {2,4,15,1,15,1,5,1}; // output/expected result

        byte[] actualResult = RleProgram.encodeRle(flatData);

        assertArrayEquals(rleData, actualResult);
    }

    @Test
    public void getDecodedLength()
    {
        byte[] flatData = {3, 15, 6, 4};
        int testValue = 9;

        assertEquals(testValue, RleProgram.getDecodedLength(flatData));
    }

    @Test
    public void decodeRle()
    {
        byte[] flatData = {2,4,15,1,15,1,5,1,1,8,1,7};
        byte[] output = {4,4,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,8,7};

        byte[] actualResult = RleProgram.decodeRle(flatData);

        assertArrayEquals(output, actualResult);
    }

    @Test
    public void stringToData()
    {
        String data = "3f64";
        byte[] output = {3, 15, 6, 4};

        assertArrayEquals(output, RleProgram.stringToData(data));

    }

    @Test
    public void toRleString()
    {
        byte[] flatData = {15, 15, 6, 4};
        String output = "15f:64";

        assertEquals(output, RleProgram.toRleString(flatData));
    }

    @Test
    public void stringToRle()
    {
        String data = "15f:64";
        byte[] flatData = {15, 15, 6, 4};
        byte[] actual = RleProgram.stringToRle(data);

        assertArrayEquals(flatData, actual);
    }
}
