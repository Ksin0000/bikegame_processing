import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.Arrays; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class bike extends PApplet {

startmenu sm;
bikegame bg;
int menu=0;// 0menu 1redme 2game 3exit
int select=2;//=constrain(select, 1, 3);
boolean enterF=false;

boolean fillanime=false;
int animejyunnbann=0;
int selectedanime;
//int s;

public void setup() {
  bg=new bikegame();
  sm=new startmenu();
  
  frameRate(60);
  background(0);
  menu=0;
  select=2;
  enterF=false;
  animejyunnbann=0;
  File f=new File(dataPath("list.txt"));
  /*
  if(!f.exists()){
    String[] aaaaa={"599999","599999","599999","599999"};
    saveStrings("list.txt", aaaaa);
  }
  */
}

public void draw() {
  if (menu==2) {
    bg.kahenmode(0);
    bg.haikei();
    if(bg.maingame(select,enterF))
      menu=0;
    enterF=false;
  } else if (menu==0) {
    bg.kahenmode(400);
    bg.haikei(); 
    if (animejyunnbann==0) {
      sm.menu(select);
      if (enterF) {
        animejyunnbann=1;
        selectedanime=select;
      }
    } else if (animejyunnbann==1) {
      if (sm.fillanime(selectedanime)) {
        animejyunnbann=2;
      }
    } else if (animejyunnbann==2) {
      sm.kirikawari(selectedanime);
        animejyunnbann=3;
    } else if (animejyunnbann==3) {
      enterF=false;
      menu=selectedanime;
      animejyunnbann=0;
      delay(3000);
    }
  } else if (menu==1) {
    bg.kahenmode(-300);
    bg.haikei();
    
    
          String[] rankhyouzi = loadStrings("list.txt");
          int[] ranking=new int[3];
          
          for(int i=0;i<3;i++){
            int tempint=Integer.parseInt(rankhyouzi[i]);
            String strtemp="TIME="+tempint/60+"."+tempint%60+"(s)";
                fill(255);
    textSize(32);
    textAlign(CENTER, CENTER);
    text(strtemp, width/2, 40+32*i);

          }
          /*
          int[] ranking={Integer.persInt(rankhyouzi[0]),Integer.persInt(rankhyouzi[1]),Integer.persInt(rankhyouzi[2]));
String temp="TIME="+ranking[0]/60+"."+ranking[0]%60+"(s)";
    fill(255);
    textSize(32);
    textAlign(CENTER, CENTER);
    text(temp, width/6+18, height-40);
    text(rankhyouzi[1], width/6*3, height-40);
    text(rankhyouzi[2], width/6*5-18, height-40);
    */
    
    if (enterF) {
      menu=0;
      enterF=false;
    }
  } else if (menu==3) {
    exit();
  }
}

public void keyPressed() {
  if (key == CODED) {
    if (!enterF&&(keyCode == RETURN||keyCode == UP)) {
      enterF=true;
    } else {
      enterF=false;
    }

    if (keyCode == LEFT) {
      select--;
    } else if (keyCode == RIGHT) {
      select++;
    }
    select=constrain(select, 1, 3);
  }
}

public void mouseMoved() {
  if (mouseX<width/3) {
    select=1;
  } else if (mouseX<width/3*2.0f) {
    select=2;
  } else {
    select=3;
  }
}

public void mouseClicked() {
  if (!enterF)
    enterF=true;
}


public class bikegame { //<>// //<>// //<>// //<>//
  int SPEED;
  int KYORI;
  final static float w_line=100;
  final static float d_line=200;
  float x_line;
  float aSpeed;
  int level;
  int koteispeed;

  boolean saisyo;

  int stime;
  int time;
  int goaltime;
  boolean goal;

  int[] aka;
  int[] ki;
  float x_mono;
  int monoCount;

  boolean mode;//true kahen ,false kotei

  bikegame() {
    SPEED=0;
    KYORI=0;
    aSpeed=0;
    x_line=1;
    saisyo=true;
    aka=new int[60];
    ki=new int[20];
  }

  public boolean maingame(int select, boolean enterF) {
    time=frameCount-stime;

    if (saisyo) {
      saisyo=false;
      SPEED=500;
      KYORI=0;
      //aSpeed=1;
      aSpeed=0;
      x_line=0;
      stime=frameCount;
      goaltime=0;
      goal=false;
      x_mono=0;
      for (int i=0; i<aka.length; i++) {
        aka[i]=(int)random(3);//0,1,2
        if (i%3==0) {
          do {
            ki[i/3]=(int)random(3);
          } while (ki[i/3]==aka[i]);
        }
      }
      monoCount=0;
    }
    x_mono-=SPEED/100;
    if (!goal) {
      SPEED=constrain(SPEED, 100, 10000);
      textSize(16);
      textAlign(LEFT, TOP);
      //text("TIME(second)="+time/60/*+":"+time%60*/, 10, 10);
      text("TIME(second)="+time/60+"."+time%60, 10, 10);
      text("SPEED="+SPEED, 10, 26);
      text("KYORI="+KYORI, 10, 42);
      for (int i=1; i<20; i++) {
        float ytemp=w_line*d_line/(x_mono+i*100*10);//siki *bairitu
        if (ytemp<0)continue;
        if (60<=i+monoCount)break;
        if (height/2<=ytemp) {
          x_mono=0;
          monoCount++;
          if (60<=i+monoCount)break;
          if (select-1==aka[monoCount])
            SPEED/=2;
          if (monoCount%3==0 && select-1==ki[monoCount/3])
            SPEED*=2;
        }


        float ellipseWidth=map(ytemp, 0, height/2, 1, width/6);
        fill(255, 0, 0);
        float ytmp=height/2+ytemp;
        float xtmp;
        float coney1=ytmp-ellipseWidth/4;
        float coney2=ytmp+ellipseWidth/4;

        float coney1l;
        float coney1r;
        float coney2l;
        float coney2r;


        if (aka[i+monoCount]==0) {
          xtmp=width/2-map(ytemp, 0, height/2, 0, width/6*2);
          coney1l=width/2-map(coney1-height/2, 0, height/2, 0, width/9*2);
          coney1r=width/2-map(coney1-height/2, 0, height/2, 0, width/9*4);
          coney2l=width/2-map(coney2-height/2, 0, height/2, 0, width/9*2);
          coney2r=width/2-map(coney2-height/2, 0, height/2, 0, width/9*4);
          quad(coney1l, coney1, coney1r, coney1, coney2r, coney2, coney2l, coney2);

          //ellipse(xtmp, ytmp, ellipseWidth, ellipseWidth/2);
          //rectMode(RADIUS);
          //rect(xtmp, ytmp, ellipseWidth/2, ellipseWidth/4);
          triangle(xtmp-ellipseWidth/2, ytmp, xtmp+ellipseWidth/2, ytmp, xtmp, ytmp-ellipseWidth);
        } else if (aka[i+monoCount]==1) {
          xtmp=width/2;
          //ellipse(xtmp, ytmp, ellipseWidth, ellipseWidth/2);
          coney1l=width/2-map(coney1-height/2, 0, height/2, 0, width/9*1);
          coney1r=width/2+map(coney1-height/2, 0, height/2, 0, width/9*1);
          coney2l=width/2-map(coney2-height/2, 0, height/2, 0, width/9*1);
          coney2r=width/2+map(coney2-height/2, 0, height/2, 0, width/9*1);
          quad(coney1l, coney1, coney1r, coney1, coney2r, coney2, coney2l, coney2);

          rectMode(RADIUS);
          rect(xtmp, ytmp, ellipseWidth/2, ellipseWidth/4);
          triangle(xtmp-ellipseWidth/2, ytmp, xtmp+ellipseWidth/2, ytmp, xtmp, ytmp-ellipseWidth);


          //      ellipse(xtmp, height/2+ytemp, ellipseWidth, ellipseWidth/2);
        } else if (aka[i+monoCount]==2) {
          xtmp=width/2+map(ytemp, 0, height/2, 0, width/6*2);
          // ellipse(xtmp, ytmp, ellipseWidth, ellipseWidth/2);
          coney1l=width/2+map(coney1-height/2, 0, height/2, 0, width/9*2);
          coney1r=width/2+map(coney1-height/2, 0, height/2, 0, width/9*4);
          coney2l=width/2+map(coney2-height/2, 0, height/2, 0, width/9*2);
          coney2r=width/2+map(coney2-height/2, 0, height/2, 0, width/9*4);
          quad(coney1l, coney1, coney1r, coney1, coney2r, coney2, coney2l, coney2);

          rectMode(RADIUS);
          rect(xtmp, ytmp, ellipseWidth/2, ellipseWidth/4);
          triangle(xtmp-ellipseWidth/2, ytmp, xtmp+ellipseWidth/2, ytmp, xtmp, ytmp-ellipseWidth);


          //    ellipse(xtmp, height/2+ytemp, ellipseWidth, ellipseWidth/2);
        }
        fill(255, 255, 0);
        if ((i+monoCount)%3==0) {
          if (ki[(i+monoCount)/3]==0) {
            ellipse(width/2-map(ytemp, 0, height/2, 0, width/6*2), height/2+ytemp, ellipseWidth, ellipseWidth/2);
          } else if (ki[(i+monoCount)/3]==1) {
            ellipse(width/2, height/2+ytemp, ellipseWidth, ellipseWidth/2);
          } else if (ki[(i+monoCount)/3]==2) {
            ellipse(width/2+map(ytemp, 0, height/2, 0, width/6*2), height/2+ytemp, ellipseWidth, ellipseWidth/2);
          }
        }
      }
    }
    fill(255);
  


    if (select==1) {
      triangle(0, height, width/3, height, width/6+18, height-20);
    } else if (select==2) {
      triangle(width/3, height, width/3*2, height, width/6*3, height-20);
    } else if (select==3) {
      triangle(width/3*2, height, width, height, width/6*5-18, height-20);
    }

    if (!goal&&400<=KYORI) {
      goal=true;
      goaltime=time;
      aSpeed=-10;

      int listsize=4;
      String[] loadrank = loadStrings("list.txt");
      int[] rankint =new int[5];
      String[] saveRank = new String[4];
      for (int i=0; i<4; i++) {
        if (i<loadrank.length&&loadrank[i]!=null)
          rankint[i]=Integer.parseInt(loadrank[i]);
        else
          rankint[i]=99999;
      }
      rankint[4]=goaltime;
      Arrays.sort(rankint);

      for (int i=0; i<4; i++) {
        saveRank[i]=String.valueOf(rankint[i]);
      }
      saveStrings("list.txt", saveRank);
    }

    if (goal) {
      if (SPEED>3000)
        SPEED=3000;
      if (SPEED<=0) {
        SPEED=0;
        aSpeed=0;
      }

      int txtsize=60;
      String temp="TIME="+goaltime/60+"."+goaltime%60+"(s)";
      rectMode(CENTER);
      textSize(txtsize);
      textAlign(CENTER, CENTER);
      noStroke();
      fill(0, 150);
      rect(width/2, height/2+10, textWidth(temp)+5, txtsize+10);
      fill(255);
      text(temp, width/2, height/2);

      if (enterF) {
        if (select==3) {
          saisyo=true;
          return true;
        } else if (select==2) {
          saisyo=true;
        }
      }
      textSize(32);
      text("retry", width/6*3, height-40);
      text("menu", width/6*5-18, height-40);
    }
    return false;
  }

  public void kahenmode(int speedmode_) {
    if (speedmode_==0) {
      mode=true;
    } else {
      mode=false;
      koteispeed=speedmode_;
    }
  }

  public void haikei() {
    x_line-=SPEED/100;
    if (mode) {
      SPEED+=aSpeed;
    } else {
      SPEED=koteispeed;
    }


    if (x_line<=-100) {
      KYORI++;
      x_line=0;
    } else if (100<x_line) {
      KYORI--;
      x_line=0;
    }

    background(0);

    stroke(255);
    line(width/2, height/2, 0, height);
    line(width/2, height/2, width, height);

    stroke(250);
    fill(250);
    int rect_size=3;
    rectMode(CENTER);
    rect(width/2, height/2, rect_size, rect_size);

    for (float x_line_temp=x_line; x_line_temp<10000; x_line_temp+=100) {
      //text(y, 10, 10*x);
      float y_line=w_line*d_line/x_line_temp;//siki *bairitu
      float line_pl=width/2+y_line;
      float line_mi=width/2-y_line;
      stroke(255, 20+y_line);
      line(line_pl, line_pl, line_pl, line_mi);
      line(line_mi, line_pl, line_mi, line_mi);

      //line(wid/2+y, hei/2+y, wid/2+y, hei/2-y);
      //line(wid/2-y, hei/2+y, wid/2-y, hei/2-y);
    }
    /*tesutohyouzi
     textSize(16);
     textAlign(LEFT, TOP);
     text("SPEED="+SPEED, 10, 26);
     text("KYORI="+KYORI, 10, 42);
     text(x_line, 10, 58);//
     */
  }
}
class startmenu {
  String[] menustr={"README", "START", "EXIT"};
  int textsize=150;

  public int menu(int selected) {
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

  public boolean fillanime(int selected) {
    if (selected==1) {
      basex1=0;
      basex2=width/3;
      tmpx1=width/2.0f-2.0f/animecount*bai*width/height;
      //tmpx2=width/2-(float)2/count*bai*(width/3/height);
      tmpx2=width/2.0f-2.0f/(float)animecount*bai*width/3.0f/height;
    } else if (selected==2) {
      basex1=width/3;
      basex2=width/3*2;
      tmpx1=width/2.0f-2.0f/animecount*bai*width/3.0f/height;
      //tmpx2=width/2-(float)2/count*bai*(-width/3/height);
      tmpx2=width/2.0f+2.0f/animecount*bai*width/3.0f/height;
    } else if (selected==3) {
      basex1=width/3*2;
      basex2=width;
      //tmpx1=width/2-(float)2/count*bai*(-width/3/height);
      //tmpx2=width/2-(float)2/count*bai*(-width/height);
      tmpx1=width/2.0f+(float)2.0f/animecount*bai*width/3.0f/height;
      tmpx2=width/2.0f+(float)2.0f/animecount*bai*width/height;
    }
    noStroke();
    fill(220);
    quad(basex2, height, basex1, height, tmpx1, tmpy, tmpx2, tmpy);

    int endtime=160;
    if (animecount<endtime) {
      animecount++;
      tmpy=width/2.0f+(float)2.0f/animecount*bai;
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
  public void kirikawari(int selected) {
    background(255);
    textAlign(CENTER, CENTER);
    textSize(textsize);
    fill(100);
    text(menustr[selected-1], width/2, height/2);
    //fill(255, 100);
    //rect(width/2, height/2-5, textWidth(menustr[selected-1])+5, textsize);
  }
}
  public void settings() {  size(750, 750); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "--present", "--window-color=#000000", "--hide-stop", "bike" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
