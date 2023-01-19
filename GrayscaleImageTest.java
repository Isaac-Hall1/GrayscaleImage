package assign01;

import org.junit.jupiter.api.BeforeEach;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GrayscaleImageTest {

    private GrayscaleImage smallSquare;
    private GrayscaleImage smallWide;
    private GrayscaleImage bigWide;
    private GrayscaleImage smallOdd;
    private GrayscaleImage bigOdd;

    @BeforeEach
    void setUp() {
        smallSquare = new GrayscaleImage(new double[][]{{1,2},{3,4}});
        smallWide = new GrayscaleImage(new double[][]{{1,2,3},{4,5,6}});
        bigWide = new GrayscaleImage(new double[][] {{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},
        	{16,17,18,19,20},{21,22,23,24,25},{26,27,28,29,30},{31,32,33,34,35}});
        smallOdd = new GrayscaleImage(new double[][] {{1,2},{3,4},{5,6},{7,8},{9,10}});
        bigOdd = new GrayscaleImage(new double[][]{{1,2},{3,4},{5,6},{7,8},{9,10},{11,12},{13,14}});
    }

    @Test
    void testGetPixel(){
    	//tests are not ordered row colum but instead colum row to fit the 
    	//y x plug in style. It is backwards but when plugged in backwards it works
        assertEquals(smallSquare.getPixel(0,0), 1);
        assertEquals(smallSquare.getPixel(1,0), 2);
        assertEquals(smallSquare.getPixel(0,1), 3);
        assertEquals(smallSquare.getPixel(1,1), 4);
        assertEquals(bigWide.getPixel(0, 2),11);

    }
    @Test
    void testGetPixelNegative(){
    	//tests to see if when a negative value is plugged in an error is thrown
        assertThrows(IllegalArgumentException.class, () -> { smallSquare.getPixel(-1,0); });
        assertThrows(IllegalArgumentException.class, () -> { smallSquare.getPixel(0,-1); });
    }
    @Test
    void testGetPixelTooBig(){
    	//test to see if when a plugged number is too big it throws an error
        assertThrows(IllegalArgumentException.class, () -> { smallSquare.getPixel(10,0); });
        assertThrows(IllegalArgumentException.class, () -> { smallSquare.getPixel(0,10); });
    }

    @Test
    void testEquals() {
    	//test if two different 2d arrays are equivelant
        assertEquals(smallSquare, smallSquare);
        var equivalent = new GrayscaleImage(new double[][]{{1,2},{3,4}});
        assertEquals(smallSquare, equivalent);
    }
    @Test
    void testEquals2() {
    	//tests whether or not two 2d arrays are equivilant even when they're big
        var equivalent = new GrayscaleImage(new double[][]{{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},
        	{16,17,18,19,20},{21,22,23,24,25},{26,27,28,29,30},{31,32,33,34,35}});
        if(equivalent.equals(bigWide)) {
        	System.out.println("equal function found them equal");
        }
        else {
        	fail ("They were equal, the function didn't catch it.");
        }
    }
    @Test
    void testEquals3() {
    	//tests if the equal function catches when two 2d arrays arent equal
    	var equivalent = new GrayscaleImage(new double[][]{{1,2,3,4,5},{6,7,8,9,10},{11,12,13,14,15},
        	{16,17,18,19,20},{21,22,23,24,25},{26,27,28,29,30},{31,32,33,34,35}});
        if(equivalent.equals(smallWide)) {
        	fail("Should not be equal");
        }
        else {
        	System.out.println("They weren't equal, the function caught it.");
        }
    }

    @Test
    void averageBrightness() {
        assertEquals(smallSquare.averageBrightness(), 2.5, 2.5*.001);
        var bigZero = new GrayscaleImage(new double[1000][1000]);
        assertEquals(bigZero.averageBrightness(), 0);
    }
    @Test
    //tests average brightness on another 2d array
    void averageBrightness2() {
        assertEquals(smallWide.averageBrightness(), 3.5, 3.5*.001);
    }

    @Test
    void normalized() {
        var smallNorm = smallSquare.normalized();
        assertEquals(smallNorm.averageBrightness(), 127, 127*.001);
        var scale = 127/2.5;
        var expectedNorm = new GrayscaleImage(new double[][]{{scale, 2*scale},{3*scale, 4*scale}});
        for(var row = 0; row < 2; row++){
            for(var col = 0; col < 2; col++){
                assertEquals(smallNorm.getPixel(col, row), expectedNorm.getPixel(col, row),
                        expectedNorm.getPixel(col, row)*.001,
                        "pixel at row: " + row + " col: " + col + " incorrect");
            }
        }
    }

    @Test
    //tests if you can normalize a bigger 2d array
    void normalized2() {
        var smallNorm = bigWide.normalized();
        assertEquals(smallNorm.averageBrightness(), 127, 127*.001);
        }
    @Test
    void mirrored() {
        var expected = new GrayscaleImage(new double[][]{{2,1},{4,3}});
        assertEquals(smallSquare.mirrored(), expected);
    }
    @Test
    //tests that a bigger 2d array can be mirrored
    void mirrored2() {
        var expected = new GrayscaleImage(new double[][]{{2,1},{4,3},{6,5},{8,7},{10,9}});
        assertEquals(smallOdd.mirrored(), expected);
    }

    @Test
    void cropped() {
        var cropped = smallSquare.cropped(1,1,1,1);
        assertEquals(cropped, new GrayscaleImage(new double[][]{{4}}));
        var cropped1 = smallWide.cropped(0,1,2,2);
        assertEquals(cropped1, new GrayscaleImage(new double[][]{{2,3},{5,6}}));
    }
    @Test
    //confirms that an IllegalArugimentException is thrown when incorrect values are given
    void croppedOutOfBounds() {
    	assertThrows(IllegalArgumentException.class, () -> { smallSquare.cropped(8,3,1,1); });
    }
    @Test
    //confirms that an IllegalArugimentException is thrown when incorrect values are given
    void croppedOutOfBoundsNegative() {
    	assertThrows(IllegalArgumentException.class, () -> { smallSquare.cropped(-1,3,1,1); });
    }

    @Test
    void squarified() {
        var squared = smallWide.squarified();
        var expected = new GrayscaleImage(new double[][]{{1,2},{4,5}});
        assertEquals(squared, expected);
    }
    @Test
    //tests with a bigger 2Darray
    void squarified2() {
        var squared = bigWide.squarified();
        var expected = new GrayscaleImage(new double[][]{{6,7,8,9,10},{11,12,13,14,15},
        	{16,17,18,19,20},{21,22,23,24,25},{26,27,28,29,30}});
        assertEquals(squared, expected);
    }
    @Test
    //tests with a small odd 2D array
    void squarified3() {
        var squared = smallOdd.squarified();
        var expected = new GrayscaleImage(new double[][]{{3,4},{5,6}});
        assertEquals(squared, expected);
    }
    @Test
    //tests with a bigger odd 2D array
    void squarified4() {
        var squared = bigOdd.squarified();
        var expected = new GrayscaleImage(new double[][]{{5,6},{7,8}});
        assertEquals(squared, expected);
    }
    
}