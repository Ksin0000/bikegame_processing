class startmenu {
  String[] menustr={"README", "START", "EXIT"};
  int textsize=150;

  int menu(int selected) {
    textSize(textsize);
    textAlign(CENTER, CENTER);
    fill(255);
    text("Bike", width/2+3, height/6+3);
    text("Game", width/2+3, height/6+textsize+3);
    text("Bike", width/2+3, height/6-3);
    text("Game", width/2+3, height/6+textsize-3);
    text("Bike", width/2-3, height/6+3);
    text("Game", width/2-3, height/6+textsize+3);
    text("Bike", width/2-3, height/6-3);
    text("Game", width/2-3, height/6+textsize-3);

    fill(0);
    text("Bike", width/2, height/6);
    text("Game", width/2, height/6+textsize);

    fill(255);
    textSize(32);
    text(menustr[0], width/6+18, height-40);
    text(menustr[1], width/6*3, height-40);
    text(menustr[2], width/6*5-18, height-40);

    if (selected==1) {
      triangle(0, height, width/3, height, width/6+18, height-20);
    } else if (selected==2) {
      triangle(width/3, height, width/3*2, height, width/6*3, height-20);
    } else if (selected==3) {
      triangle(width/3*2, height, width, height, width/6*5-18, height-20);
    }
    return selected;
  }

  int animecount=0;
  int bai=height;//nantonaku

  float tmpy;
  float tmpx1;
  float tmpx2;
  float basex1;
  float basex2;

  boolean fillanime(int selected) {
    if (selected==1) {
      basex1=0;
      basex2=width/3;
      tmpx1=width/2.0-2.0/animecount*bai*width/height;
      //tmpx2=width/2-(float)2/count*bai*(width/3/height);
      tmpx2=width/2.0-2.0/(float)animecount*bai*width/3.0/height;
    } else if (selected==2) {
      basex1=width/3;
      basex2=width/3*2;
      tmpx1=width/2.0-2.0/animecount*bai*width/3.0/height;
      //tmpx2=width/2-(float)2/count*bai*(-width/3/height);
      tmpx2=width/2.0+2.0/animecount*bai*width/3.0/height;
    } else if (selected==3) {
      basex1=width/3*2;
      basex2=width;
      //tmpx1=width/2-(float)2/count*bai*(-width/3/height);
      //tmpx2=width/2-(float)2/count*bai*(-width/height);
      tmpx1=width/2.0+(float)2.0/animecount*bai*width/3.0/height;
      tmpx2=width/2.0+(float)2.0/animecount*bai*width/height;
    }
    noStroke();
    fill(220);
    quad(basex2, height, basex1, height, tmpx1, tmpy, tmpx2, tmpy);

    int endtime=160;
    if (animecount<endtime) {
      animecount++;
      tmpy=width/2.0+(float)2.0/animecount*bai;
    }

    if (0<=animecount) {
      noFill();
      rectMode(CENTER);
      float forsize=map(animecount, 0, endtime, 1, width);
      for (int i=0; i<forsize; i++) {
        stroke(255, map(i, 0, forsize, 270, 0));
        rect(width/2, height/2, i*2, i*2);
      }
    }
    if (endtime==animecount) {
      animecount=0;
      return true;
    }
    return false;
  }
  void kirikawari(int selected) {
    background(255);
    textAlign(CENTER, CENTER);
    textSize(textsize);
    fill(100);
    text(menustr[selected-1], width/2, height/2);
    //fill(255, 100);
    //rect(width/2, height/2-5, textWidth(menustr[selected-1])+5, textsize);
  }
}
