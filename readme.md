# Simple JPEG Decoder

Right now it is not perfect. Doesn't support CMYK and progressive JPEGs. For rest should work.
Performance right now is pretty good and even better than [ImageIO](http://docs.oracle.com/javase/7/docs/api/javax/imageio/ImageIO.html).
There few places for optimization right now :
 * Improve inverse DCT algorithm;
 * Improve block merging module (BlockMerger) - replace 2 dimension array with 1 dimension so it will be cache friendly;
 * Probably improve huffman decoding somehow;
 
Any suggestions are welcomed.


## Usage
`BufferedImage bi = JpegDecoder.decode(Paths.get(path, "testImage.jpg"));`

### Test results for color image 887x707
```
Benchmark                         Mode  Samples   Score  Score error  Units
c.d.PeformanceTest.ImageIO        avgt       10  52.028        2.160  ms/op
c.d.PeformanceTest.JpegDecoder    avgt       10  47.663        1.288  ms/op
```

Please have in mind that results are not precise enough so may differs on different architectures.
