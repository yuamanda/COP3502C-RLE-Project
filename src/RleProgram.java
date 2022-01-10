import java.util.Scanner;

public class RleProgram
{
    public static void main(String[] args)
    {
        // declare scanner
        Scanner scan = new Scanner(System.in);

        // declare and initialize variables
        String str;
        String rle;

        int menuNumber = 1;
        byte[] imageData = null;

        // 1. Display welcome message
        System.out.println("Welcome to the RLE image encoder!");
        System.out.println();

        // 2. Display color tests with the message
        System.out.println("Displaying Spectrum Image:");
        ConsoleGfx.displayImage(ConsoleGfx.testRainbow);
        System.out.println();
        System.out.println();

        while(menuNumber != 0)
        {
            // 3. Display the menu - Part A: while loop or if-else chains
            System.out.println("RLE Menu");
            System.out.println("--------");
            System.out.println("0. Exit");
            System.out.println("1. Load File");
            System.out.println("2. Load Test Image");
            System.out.println("3. Read RLE String");
            System.out.println("4. Read RLE Hex String");
            System.out.println("5. Read Data Hex String");
            System.out.println("6. Display Image");
            System.out.println("7. Display RLE String");
            System.out.println("8. Display Hex RLE Data");
            System.out.println("9. Display Hex Flat Data");
            System.out.println();

            // 4. Prompt for input
            System.out.print("Select a Menu Option: ");
            menuNumber = scan.nextInt();

            // option 1, option 2, and option 6
            // user might first enter 1 -> 6 or 2 -> 6
            // complete option 0, 3, 4, 5, 7, 8, 9
            if(menuNumber == 1)
            {
                // 3.1 - option 1
                // ConsoleGfx.loadFile(userInput) and you want to store the returned byte[] into imageData array
                System.out.print("Enter name of file to load: ");
                String userInput = scan.next();
                imageData = ConsoleGfx.loadFile(userInput);
            }
            else if(menuNumber == 2)
            {
                // 3.2 - option 2
                // store ConsoleGfx.testImage into the imageData array
                System.out.println("Test image data loaded.");
                imageData = ConsoleGfx.testImage;
            }
            else if(menuNumber == 3)
            {
                System.out.println("Enter an RLE string to be decoded: ");
                // scans in user input
                str = scan.next();
                // calls stringToRle method with input str and then calls decodeRle method with input imageData
                imageData = stringToRle(str);
                imageData = decodeRle(imageData);
            }
            else if(menuNumber == 4)
            {
                System.out.println("Enter the hex string holding RLE data: ");
                // scans in user input
                str = scan.next();
                // calls stringToData method with input str and then calls decodeRle method with input imageData
                imageData = stringToData(str);
                imageData = decodeRle(imageData);
            }
            else if(menuNumber == 5)
            {
                System.out.println("Enter the hex string holding flat data: ");
                // scans in user input
                str = scan.next();
                // calls stringToData method with input str
                imageData = stringToData(str);
            }
            else if(menuNumber == 6)
            {
                // 3.6 - option 6
                // display image stored inside imageData array
                System.out.print("Displaying image...");
                // calls displayImage with input imageData
                ConsoleGfx.displayImage(imageData);
            }
            else if(menuNumber == 7)
            {
                // calls encodeRle with input imageData and then calls toRleString with imageData
                imageData = encodeRle(imageData);
                rle = toRleString(imageData);
                System.out.println("RLE representation: " + rle);
            }
            else if(menuNumber == 8)
            {
                // calls encodeRle with input imageData and then calls toHexString with input imageData
                imageData = encodeRle(imageData);
                rle = toHexString(imageData);
                System.out.println("RLE hex values: " + rle);
            }
            else if(menuNumber == 9)
            {
                // calls toHexString with input imageData
                rle = toHexString(imageData);
                System.out.println("Flat hex values: " + rle);
            }
        }
    }

    // translates data (RLE or raw) a hexadecimal string (without delimiters)
    public static String toHexString(byte[] data)
    {
        // declare and initialize variables
        String hex = "";

        // loops through the array byte[] data
        for(int i = 0; i < data.length; i++)
        {
            // if data[i] is less than or equal to 10, add integer to string
            if(data[i] < 10)
            {
                hex += data[i];
            }
            // else if data[i] equals 10, add 'a' to string
            else if(data[i] == 10)
            {
                hex += 'a';
            }
            // else if data[i] equals 10, add 'b' to string
            else if(data[i] == 11)
            {
                hex += 'b';
            }
            // else if data[i] equals 10, add 'c' to string
            else if(data[i] == 12)
            {
                hex += 'c';
            }
            // else if data[i] equals 10, add 'd' to string
            else if(data[i] == 13)
            {
                hex += 'd';
            }
            // else if data[i] equals 10, add 'e' to string
            else if(data[i] == 14)
            {
                hex += 'e';
            }
            // else if data[i] equals 10, add 'f' to string
            else if(data[i] == 15)
            {
                hex += 'f';
            }
        }
        // returns String hex
        return hex;
    }

    public static int countRuns(byte[] flatData)
    {
        // declare and initialize variables
        int runs = 1;
        int count = 0;

        // loops through the array byte[] flatData
        for(int i = 0; i < flatData.length - 1; i++)
        {
            // if flatData[i] equals flatData[i + 1], then increase count by 1
            if(flatData[i] == flatData[i + 1])
            {
                count++;
            }
            // else increase runs by 1 and reset count to 0
            else
            {
                runs++;
                count = 0;
            }
            // if count equals 15, increase runs by 1 and reset count ot 0
            if(count == 15)
            {
                runs++;
                count = 0;
            }
        }
        // returns int runs
        return runs;
    }

    public static byte[] encodeRle(byte[] flatData)
    {
        // declare and initialize variables
        int size = countRuns(flatData) * 2;
        byte[] encoded = new byte[size];
        byte count = 1;
        int arrayIndex = 0;
        int i;

        // loops through the array byte[] flatData
        for(i = 0; i < flatData.length - 1; i++) {
            // if flatData[i] equals flatData[i + 1]
            if (flatData[i] == flatData[i + 1]) {
                // if count equals 15: encoded[arrayIndex] = count, encoded[arrayIndex + 1] = flatData[i], add 2 to arrayIndex, reset count to 1, and continue through the loop
                if (count == 15) {
                    encoded[arrayIndex] = count;
                    encoded[arrayIndex + 1] = flatData[i];
                    arrayIndex += 2;
                    count = 1;
                    continue;
                }
                count++;
            }
            // else encoded[arrayIndex] = count, encoded[arrayIndex + 1] = flatData[i], add 2 to arrayIndex, and reset count to 1
            else {
                encoded[arrayIndex] = count;
                encoded[arrayIndex + 1] = flatData[i];
                arrayIndex += 2;
                count = 1;
            }
        }
        encoded[arrayIndex] = count;
        encoded[arrayIndex + 1] = flatData[i];

        // returns array byte[] encoded
        return encoded;
    }

    public static int getDecodedLength(byte[] rleData)
    {
        // declare and initialize variables
        int size = 0;

        // loops through byte[] rleData, adds every other number to int size
        for(int i = 0; i < rleData.length; i += 2)
        {
            size += rleData[i];
        }
        // returns int size
        return size;
    }

    public static byte[] decodeRle(byte[] rleData)
    {
        // declare and initialize variables
        byte[] encoded = new byte[getDecodedLength(rleData)];
        int index = 0;

        // loops through byte[] rleData
        for(int i = 0; i < rleData.length; i += 2)
        {
            // declare and initialize variables
            int count = rleData[i];

            // while count is greater than 0: encoded[index] = rleData[i + 1], add 1 to index, and subtract one from count
            while(count > 0)
            {
                encoded[index] = rleData[i + 1];
                index += 1;
                count--;
            }
        }
        // returns byte[] encoded
        return encoded;
    }

    public static byte[] stringToData(String dataString)
    {
        // declare and initialize variables
        byte[] stringToData = new byte[dataString.length()];

        // loops through length of dataString
        for(int i = 0; i < dataString.length(); i++)
        {
            // if dataString is a character at i, then add it to stringToData[i]
            if(Character.isDigit(dataString.charAt(i)))
            {
                stringToData[i] = Byte.parseByte(String.valueOf(dataString.charAt(i)));
            }
            // if dataString is 'a', then stringToData[i] = 10
            else if(dataString.charAt(i) == 'a')
            {
                stringToData[i] = 10;
            }
            // if dataString is 'b', then stringToData[i] = 11
            else if(dataString.charAt(i) == 'b')
            {
                stringToData[i] = 11;
            }
            // if dataString is 'c', then stringToData[i] = 12
            else if(dataString.charAt(i) == 'c')
            {
                stringToData[i] = 12;
            }
            // if dataString is 'd', then stringToData[i] = 13
            else if(dataString.charAt(i) == 'd')
            {
                stringToData[i] = 13;
            }
            // if dataString is 'e', then stringToData[i] = 14
            else if(dataString.charAt(i) == 'e')
            {
                stringToData[i] = 14;
            }
            // if dataString is 'f', then stringToData[i] = 15
            else if(dataString.charAt(i) == 'f')
            {
                stringToData[i] = 15;
            }
        }
        // returns byte[] stringToData
        return stringToData;
    }

    public static String toRleString(byte[] rleData)
    {
        // declare and initialize variables
        String hex = "";

        // loops through length of rleData
        for(int i = 0; i < rleData.length; i += 2)
        {
            // add rleData[i] to String hex
            hex += rleData[i];

            // if rleData[i + 1] is less than or equal to 9, add to String hex
            if(rleData[i + 1] <= 9)
            {
                hex += rleData[i + 1];
            }
            // if rleData[i + 1] is 10, add 'a' to String hex
            else if(rleData[i + 1] == 10)
            {
                hex += 'a';
            }
            // if rleData[i + 1] is 11, add 'b' to String hex
            else if(rleData[i + 1] == 11)
            {
                hex += 'b';
            }
            // if rleData[i + 1] is 12, add 'c' to String hex
            else if(rleData[i + 1] == 12)
            {
                hex += 'c';
            }
            // if rleData[i + 1] is 13, add 'd' to String hex
            else if(rleData[i + 1] == 13)
            {
                hex += 'd';
            }
            // if rleData[i + 1] is 14, add 'e' to String hex
            else if(rleData[i + 1] == 14)
            {
                hex += 'e';
            }
            // if rleData[i + 1] is 15, add 'f' to String hex
            else if(rleData[i + 1] == 15)
            {
                hex += 'f';
            }
            // stops adding ":" after the last element
            if(i < rleData.length - 2)
            {
                hex += ':';
            }
        }
        return hex;
    }

    public static byte[] stringToRle(String rleString)
    {
        // declare and initialize variables
        String[] strs = rleString.split(":");
        byte[] data = new byte[strs.length * 2];
        int count = 0;
        String first;
        String second;

        // loops through length of strs.length
        for(int i = 0; i < strs.length; i++)
        {
            // if strs[i].length() has a length of 2
            if(strs[i].length() == 2)
            {
                // creates first and second substring
                first = strs[i].substring(0, 1);
                second = strs[i].substring(1);

                // data[count] is assigned the first(parse to a byte)
                data[count] = Byte.parseByte(first);
                // data[count + 1] is assigned second(parse to a byte with a base of 16)
                data[count + 1] = Byte.parseByte(second, 16);
            }

            // if strs[i].length() has a length of 3
            else if(strs[i].length() == 3)
            {
                // creates first and second substring
                first = strs[i].substring(0, 2);
                second = strs[i].substring(2);

                // data[count] is assigned the first(parse to a byte)
                data[count] = Byte.parseByte(first);
                // data[count + 1] is assigned second(parse to a byte with a base of 16)
                data[count + 1] = Byte.parseByte(second, 16);
            }
            // add count to 2 to keep adding to data in order
            count += 2;
        }
        return data;
    }
}
