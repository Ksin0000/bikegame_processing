import java.util.Arrays; //<>//

public class bikegame { //<>// //<>// //<>// //<>// //<>//
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

  boolean maingame(int select, boolean enterF) {
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

  void kahenmode(int speedmode_) {
    if (speedmode_==0) {
      mode=true;
    } else {
      mode=false;
      koteispeed=speedmode_;
    }
  }

  void haikei() {
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
