## Magnet Imager

I created this project as a short experiment into drawing images using magnets in a 
grid pattern.  The motivation was to create a screen that can accurately display an images
in magnetic sand using only electromagnets below a sand-filled tray.  The magnets can vary
their strength by changing the voltage flowing through them, but that is all.  

## How it works

This simulation uses metaballs (https://en.wikipedia.org/wiki/Metaballs) to act as a naive
approximation for the behaivior of an electromagnet.  This works because magnetic fields merge
in a similar way to metaballs (ignoring field lines), but metaballs are much **much** faster
to calculate.  

The algorithm works as follows:

1: Take an input picture and run edge detection on it\
2: Take the edge detected image and make the regions inside of the outline black and the regions
outside the outline white.\
3: Find magnets within the black region, all other magnets are set to strength 0.  This is because
no magnet outside the region of the image should be turned on.  It will always make the 
approximation worse.\
4: Out of the set of all magnets inside the region, iterate over them and shift their 
strength up or down slightly to increase the accuracy of the image.  The accuracy is measured
as the difference between the amount of accurate pixels in the magnet image and the accurate pixels in the 
original.  As the algorithm runs it makes smaller and smaller changes each iteration. 

## An example

![Input Image](cat.png)

