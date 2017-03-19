#include <math.h>
#include <Adafruit_NeoPixel.h>
#define PIN 6

Adafruit_NeoPixel strip = Adafruit_NeoPixel(142, PIN, NEO_GRB + NEO_KHZ800);
float breakLenF = 142/3; 
int breakLen = (int) breakLenF;
int pos = 0;

void setup() {
  // put your setup code here, to run once:
  strip.setBrightness(255);

  strip.begin();
  strip.show();
}

float rad(float degrees) {
    return degrees * (3.141492/180);
}

void loop() {
  // put your main code here, to run repeatedly:
    pos++;
    if(pos >= 142) pos = 0;
    int section = 0; 
    for(int i = 0; i < strip.numPixels(); i++) {
      float r;
      float g; 
      float b;
      
      if(i%breakLen == 0 && i!= 0) section++; 

      if(section == 0) {
        r=((breakLenF-i)/breakLenF) * 255;
        g=(i/breakLenF) * 255;
        b=0;
      } else if(section == 1) {
        r=0;
        g=((breakLenF-(i-breakLenF))/breakLenF) * 255;
        b=((i-breakLenF)/breakLenF) * 255;;
      } else {
         r=((i-breakLenF*2)/breakLenF) * 255;;
         g=0;
         b=((breakLenF-(i-breakLenF*2))/breakLenF) * 255;
      }

        
     int index = i+pos;
   
     if(index > strip.numPixels()-1) index = index-(strip.numPixels()-1); 
     strip.setPixelColor(index, (int)r, (int)g, (int)b);
    }
    strip.show();
    //delay(10);
}

