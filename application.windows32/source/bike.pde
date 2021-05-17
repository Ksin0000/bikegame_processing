startmenu sm;
bikegame bg;
int menu=0;// 0menu 1redme 2game 3exit
int select=2;//=constrain(select, 1, 3);
boolean enterF=false;

boolean fillanime=false;
int animejyunnbann=0;
int selectedanime;
//int s;

void setup() {
  bg=new bikegame();
  sm=new startmenu();
  size(750, 750);
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

void draw() {
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

void keyPressed() {
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

void mouseMoved() {
  if (mouseX<width/3) {
    select=1;
  } else if (mouseX<width/3*2.0) {
    select=2;
  } else {
    select=3;
  }
}

void mouseClicked() {
  if (!enterF)
    enterF=true;
}
