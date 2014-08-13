# Simple JPEG Decoder

Right now it is not perfect. Doesn't support CMYK and progressive JPEGs. For rest should work.
Performance right now is pretty good and almost equal to ImageIO. In plans - make it faster than ImageIO.
There few places for optimization right now.


## Usage
` BufferedImage bi = JpegDecoder.decode(Paths.get(path, "testImage2.jpg"));