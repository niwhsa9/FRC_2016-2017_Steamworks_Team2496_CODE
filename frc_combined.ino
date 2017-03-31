
#include <Adafruit_NeoPixel.h>
#define PIN 6

Adafruit_NeoPixel strip = Adafruit_NeoPixel(142, PIN, NEO_GRB + NEO_KHZ800);

bool dir = true;
int start =0;
float breakLenF = 142/3; 
int breakLen = (int) breakLenF;
int pos = 0;
bool rwFlag = false;

unsigned long changeTime = 0;
int effect = 0;

uint32_t color1[3] = {
  strip.Color(255,0,0), 
  strip.Color(0,255,0),
  strip.Color(255,0,255),
}; 
 int color = -1;


uint32_t color2[3] = {
  strip.Color(0,0,255),
  strip.Color(255,255,0),
  strip.Color(58,255,241),
}; 

int half = 142/2;


void setup() {
  // put your setup code here, to run once:
  strip.setBrightness(255);

  strip.begin();
  
  strip.show();
}

void mergeEffect() {
   color++;
    if(color>=3) color = 0;
    
    
    for(int i = 0; i < strip.numPixels()/2; i++) {
      if(dir == true) {
        strip.setPixelColor(i, color1[color]);
        strip.setPixelColor(strip.numPixels()-i, color2[color]);
      } else {
        strip.setPixelColor(strip.numPixels()/2-i, color1[color]);
        strip.setPixelColor(strip.numPixels()/2+i, color2[color]);
      }
       delay(10);
       strip.show();

    }
    dir = !dir;
}

void rainbowEffect() {
  pos+=3;
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
}

void rwbEffect() {
  rwFlag = !rwFlag;
  for(int i = 0; i<strip.numPixels()/2; i++) {
      strip.setPixelColor(i,0,0,255);
  }
  for(int i = strip.numPixels()/2; i<strip.numPixels(); i++) {
    if(rwFlag == true) {
      if(i%2==0) strip.setPixelColor(i,255,0, 0);
      else strip.setPixelColor(i,255,255, 255);
    } else{
       if((i+1)%2==0) strip.setPixelColor(i,255,0, 0);
       else strip.setPixelColor(i,255,255, 255);
    }
   
  }
  strip.show();
  delay(100);
}

void clearAll() {
  for(int i = 0; i < strip.numPixels(); i++) {
      strip.setPixelColor(i, 0, 0, 0);
  }
}

void loop() {
    if(millis()-changeTime >= 5000) {
      clearAll();
      changeTime = millis();
      effect++;
      if(effect >=3) effect =0;
    }
  
    if(effect == 0) rainbowEffect();
    else if (effect == 1) mergeEffect();
    else if (effect == 2) rwbEffect();
   

   
}

