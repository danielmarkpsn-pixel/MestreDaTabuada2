package com.danielmarques.mestredatabuada;

import android.app.*;
import android.os.*;
import android.content.*;
import android.graphics.*;
import android.graphics.drawable.GradientDrawable;
import android.media.*;
import android.view.*;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class MainActivity extends Activity {
    LinearLayout root, content; SharedPreferences p; Random rnd = new Random();
    ToneGenerator tone; CountDownTimer timer;
    int table, q, score, correct, lives, streak, coins, level, seconds, answer;
    boolean timed; String child, avatar;
    final int NAVY=Color.rgb(18,20,43), BLUE=Color.rgb(39,91,170), GOLD=Color.rgb(255,193,55), GREEN=Color.rgb(35,184,117), ORANGE=Color.rgb(255,139,53), PURPLE=Color.rgb(113,76,196), RED=Color.rgb(224,74,91), TEXT=Color.rgb(33,37,59), MUTED=Color.rgb(100,106,130), BG=Color.rgb(244,247,252);

    @Override public void onCreate(Bundle b){super.onCreate(b); getWindow().setStatusBarColor(NAVY); getWindow().setNavigationBarColor(NAVY);
        p=getSharedPreferences("data",0); coins=p.getInt("coins",0); level=p.getInt("level",1); child=p.getString("child","Meu Filho"); avatar=p.getString("avatar","🦸‍♂️ Herói");
        tone=new ToneGenerator(AudioManager.STREAM_NOTIFICATION,80); home(); }

    TextView text(String s,float size,int color){TextView t=new TextView(this);t.setText(s);t.setTextSize(size);t.setTextColor(color);t.setGravity(Gravity.CENTER_VERTICAL);t.setPadding(dp(8),dp(6),dp(8),dp(6));return t;}
    int dp(int n){return (int)(n*getResources().getDisplayMetrics().density+.5f);}
    GradientDrawable bg(int color,float radius){GradientDrawable g=new GradientDrawable();g.setColor(color);g.setCornerRadius(dp((int)radius));return g;}
    GradientDrawable gradient(int a,int b,float radius){GradientDrawable g=new GradientDrawable(GradientDrawable.Orientation.TL_BR,new int[]{a,b});g.setCornerRadius(dp((int)radius));return g;}
    Button button(String s,int color){Button b=new Button(this);b.setText(s);b.setTextSize(16);b.setTextColor(Color.WHITE);b.setAllCaps(false);b.setGravity(Gravity.CENTER);b.setPadding(dp(10),0,dp(10),0);b.setBackground(gradient(color,Color.rgb(
    Math.max(0, Color.red(color) - 25),
    Math.max(0, Color.green(color) - 25),
    Math.max(0, Color.blue(color) - 25)
),18));b.setElevation(dp(3));LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,dp(56));lp.setMargins(0,dp(7),0,dp(7));b.setLayoutParams(lp);return b;}
    void base(String title,String subtitle){stop();root=new LinearLayout(this);root.setOrientation(LinearLayout.VERTICAL);root.setPadding(dp(16),dp(10),dp(16),dp(12));root.setBackgroundColor(BG);setContentView(root);
        LinearLayout bar=new LinearLayout(this);bar.setGravity(Gravity.CENTER_VERTICAL);TextView back=text("‹",40,NAVY);back.setGravity(Gravity.CENTER);back.setOnClickListener(v->home());bar.addView(back,new LinearLayout.LayoutParams(dp(48),dp(58)));
        LinearLayout titles=new LinearLayout(this);titles.setOrientation(LinearLayout.VERTICAL);TextView h=text(title,22,TEXT);h.setTypeface(null,1);titles.addView(h,new LinearLayout.LayoutParams(-1,dp(32)));if(subtitle!=null){TextView sub=text(subtitle,12,MUTED);titles.addView(sub,new LinearLayout.LayoutParams(-1,dp(22)));}bar.addView(titles,new LinearLayout.LayoutParams(0,dp(58),1));
        root.addView(bar);content=new LinearLayout(this);content.setOrientation(LinearLayout.VERTICAL);ScrollView s=new ScrollView(this);s.setFillViewport(true);s.addView(content);root.addView(s,new LinearLayout.LayoutParams(-1,0,1));}
    void section(String s){TextView t=text(s,15,MUTED);t.setTypeface(null,1);t.setPadding(dp(6),dp(14),dp(6),dp(4));content.addView(t);}
    void add(String s,View.OnClickListener l){add(s,BLUE,l);} void add(String s,int color,View.OnClickListener l){Button b=button(s,color);b.setOnClickListener(l);content.addView(b);}
    void save(){p.edit().putInt("coins",coins).putInt("level",level).putString("child",child).putString("avatar",avatar).apply();}
    TextView card(String s,float size){TextView t=text(s,size,TEXT);t.setBackground(bg(Color.WHITE,24));t.setElevation(dp(3));t.setPadding(dp(18),dp(16),dp(18),dp(16));LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,dp(120));lp.setMargins(0,dp(5),0,dp(12));content.addView(t,lp);return t;}

    void home(){base("MESTRE DA TABUADA 5.0","Aprender também pode ser uma grande aventura!");
        MascotView hero=new MascotView(this);hero.setBackground(gradient(NAVY,Color.rgb(38,78,135),28));LinearLayout.LayoutParams hp=new LinearLayout.LayoutParams(-1,dp(210));hp.setMargins(0,dp(2),0,dp(12));content.addView(hero,hp);
        LinearLayout stats=new LinearLayout(this);stats.setPadding(dp(10),dp(8),dp(10),dp(8));stats.setBackground(bg(Color.WHITE,22));stats.setGravity(Gravity.CENTER);stats.addView(text("👤 "+child,14,TEXT),new LinearLayout.LayoutParams(0,dp(44),1));stats.addView(text("🪙 "+coins,14,TEXT),new LinearLayout.LayoutParams(0,dp(44),1));stats.addView(text("❤️ 3",14,TEXT),new LinearLayout.LayoutParams(0,dp(44),1));content.addView(stats,new LinearLayout.LayoutParams(-1,dp(60)));
        section("AVENTURA");add("▶  Jogar agora",GREEN,v->map());add("🗺  Mapa de aventura",ORANGE,v->map());
        section("TREINAMENTO");add("📚  Modo treino",PURPLE,v->choose(false));add("⚡  Desafio contra o tempo",BLUE,v->choose(true));add("🎁  Tabuada surpresa",ORANGE,v->surprise());
        section("RECOMPENSAS");add("🛍  Loja e personagens",PURPLE,v->shop());add("🏆  Conquistas",BLUE,v->achievements());add("🎁  Recompensa diária",GREEN,v->daily());
        section("FAMÍLIA");add("👨‍👩‍👧  Área dos pais",Color.rgb(22,143,132),v->parents());add("⚙  Configurações",Color.DKGRAY,v->settings());
        TextView credit=text("Desenvolvido por Daniel Marques via IA",12,MUTED);credit.setGravity(Gravity.CENTER);content.addView(credit,new LinearLayout.LayoutParams(-1,dp(45)));
    }

    void map(){base("Mapa de aventura","Explore mundos e conquiste estrelas");
        card("⭐ Progresso geral\n"+totalStars()+" estrelas  •  Nível "+level+"\nComplete as fases para desbloquear novos mundos.",16);
        worldCard("🌳","MUNDO 1","Floresta da Tabuada",GREEN,1,true,v->world(1));
        worldCard("🏔","MUNDO 2","Montanha dos Números",BLUE,6,worldUnlocked(6),v->world(2));
        worldCard("🚀","MUNDO 3","Espaço Matemático",PURPLE,8,worldUnlocked(8),v->world(3));
        worldCard("🏰","MUNDO 4","Castelo do Mestre",ORANGE,10,worldUnlocked(10),v->world(4));
        add("🏠 Voltar ao início",NAVY,v->home());
    }
    void worldCard(String icon,String world,String name,int color,int phase,boolean open,View.OnClickListener l){LinearLayout c=new LinearLayout(this);c.setGravity(Gravity.CENTER_VERTICAL);c.setPadding(dp(14),dp(10),dp(12),dp(10));c.setBackground(bg(Color.WHITE,22));c.setElevation(dp(2));TextView i=text(icon,34,TEXT);i.setGravity(Gravity.CENTER);c.addView(i,new LinearLayout.LayoutParams(dp(58),dp(68)));LinearLayout mid=new LinearLayout(this);mid.setOrientation(LinearLayout.VERTICAL);TextView a=text(world,12,MUTED);a.setTypeface(null,1);mid.addView(a);TextView b=text(name,18,TEXT);b.setTypeface(null,1);mid.addView(b);TextView d=text(open?"Desbloqueado":"🔒 Complete fases anteriores",12,open?GREEN:MUTED);mid.addView(d);c.addView(mid,new LinearLayout.LayoutParams(0,dp(78),1));TextView star=text(open?"★":"🔒",24,open?GOLD:MUTED);c.addView(star,new LinearLayout.LayoutParams(dp(50),dp(70)));c.setOnClickListener(v->{if(open)l.onClick(v);else toast("🔒 Complete as fases anteriores primeiro.");});LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,dp(92));lp.setMargins(0,dp(5),0,dp(5));content.addView(c,lp);}
    boolean worldUnlocked(int phase){return phase==6?p.getInt("best_5",0)>=5:phase==8?p.getInt("best_7",0)>=5:p.getInt("best_9",0)>=5;}
    void world(int w){base("Mundo "+w,""+(w==1?"🌳 Floresta da Tabuada":w==2?"🏔 Montanha dos Números":w==3?"🚀 Espaço Matemático":"🏰 Castelo do Mestre"));int start=w==1?1:w==2?6:w==3?8:10;int end=w==1?5:w==2?7:w==3?9:10;for(int i=start;i<=end;i++){final int n=i;int best=p.getInt("best_"+i,0);boolean open=i==start||p.getInt("best_"+(i-1),0)>=5;String stars=stars(best);LinearLayout c=new LinearLayout(this);c.setGravity(Gravity.CENTER_VERTICAL);c.setPadding(dp(16),0,dp(16),0);c.setBackground(bg(Color.WHITE,20));TextView num=text(""+i,25,TEXT);num.setGravity(Gravity.CENTER);num.setBackground(bg(open?Color.rgb(235,244,255):Color.rgb(232,233,238),50));c.addView(num,new LinearLayout.LayoutParams(dp(54),dp(54)));TextView tx=text((open?"Fase ":"🔒 Fase ")+i+"\nTabuada do "+i+"     "+stars,16,TEXT);c.addView(tx,new LinearLayout.LayoutParams(0,dp(70),1));c.setOnClickListener(v->{if(open)start(n,false);else toast("🔒 Termine a fase anterior com 5 acertos ou mais.");});LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,dp(78));lp.setMargins(0,dp(5),0,dp(5));content.addView(c,lp);}add("🏠 Mapa",NAVY,v->map());}
    String stars(int best){return best==0?"☆☆☆":best<7?"★☆☆":best<10?"★★☆":"★★★";}int totalStars(){int x=0;for(int i=1;i<=10;i++){int b=p.getInt("best_"+i,0);x+=b>=10?3:b>=7?2:b>=5?1:0;}return x;}

    void choose(boolean speed){base(speed?"Desafio contra o tempo":"Modo treino",speed?"30 segundos de pura diversão":"Pratique no seu ritmo");section("ESCOLHA A TABUADA");for(int i=1;i<=10;i++){final int n=i;add("✖  Tabuada do "+i, speed?BLUE:PURPLE,v->start(n,speed));}add("🎲  Mistura surpresa",ORANGE,v->start(0,speed));}
    void start(int n,boolean speed){table=n;timed=speed;q=0;score=0;correct=0;lives=3;streak=0;seconds=speed?30:0;ask();}
    void ask(){stop();base(timed?"⚡ Desafio":"Fase "+(table==0?"Surpresa":table),timed?"Responda o máximo que conseguir":"10 questões para conquistar estrelas");
        LinearLayout stats=new LinearLayout(this);stats.setGravity(Gravity.CENTER);stats.setBackground(bg(Color.WHITE,18));stats.addView(text("❤️ "+hearts(lives),15,TEXT),new LinearLayout.LayoutParams(0,dp(48),1));stats.addView(text("⭐ "+score,15,TEXT),new LinearLayout.LayoutParams(0,dp(48),1));stats.addView(text("🔥 "+streak,15,TEXT),new LinearLayout.LayoutParams(0,dp(48),1));stats.addView(text(""+(q+1)+"/10",15,TEXT),new LinearLayout.LayoutParams(0,dp(48),1));content.addView(stats);
        TextView tm=text(timed?"⏱ "+seconds+" segundos":"🎯 Escolha a resposta e toque em Verificar",14,MUTED);tm.setGravity(Gravity.CENTER);content.addView(tm,new LinearLayout.LayoutParams(-1,dp(42)));
        if(timed)timer=new CountDownTimer(seconds*1000,1000){public void onTick(long m){seconds=(int)(m/1000);tm.setText("⏱ "+seconds+" segundos");}public void onFinish(){end();}}.start();
        int a=table==0?1+rnd.nextInt(10):table,b=1+rnd.nextInt(10);answer=a*b;TextView qv=text("Quanto é\n"+a+" × "+b+" ?",34,TEXT);qv.setTypeface(null,1);qv.setGravity(Gravity.CENTER);qv.setBackground(gradient(Color.WHITE,Color.rgb(237,243,255),24));LinearLayout.LayoutParams qp=new LinearLayout.LayoutParams(-1,dp(155));qp.setMargins(0,dp(10),0,dp(14));content.addView(qv,qp);
        EditText e=new EditText(this);e.setHint("Digite a resposta");e.setTextSize(26);e.setInputType(2);e.setGravity(Gravity.CENTER);e.setSingleLine(true);e.setBackground(bg(Color.WHITE,18));content.addView(e,new LinearLayout.LayoutParams(-1,dp(64)));
        add("✓  Verificar resposta",GREEN,v->{String s=e.getText().toString().trim();if(s.isEmpty()){toast("Digite uma resposta!");return;}int val=Integer.parseInt(s);if(val==answer){correct++;streak++;int gain=10+streak*2;score+=gain;coins+=2+(streak%5==0?3:0);tone.startTone(ToneGenerator.TONE_PROP_ACK,120);toast("🎉 Muito bem! +"+gain+" pontos");}else{lives--;streak=0;tone.startTone(ToneGenerator.TONE_PROP_NACK,120);toast("💡 A resposta correta é "+answer);}q++;save();hideKeyboard(e);if(lives<=0||q>=10)end();else ask();});
        add("💡 Pular questão",ORANGE,v->{streak=0;q++;if(q>=10)end();else ask();});add("✕ Sair",NAVY,v->home());
    }
    String hearts(int n){String s="";for(int i=0;i<3;i++)s+=i<n?"❤️":"🖤";return s;}
    void end(){stop();if(table>0){int best=p.getInt("best_"+table,0);if(correct>best)p.edit().putInt("best_"+table,correct).apply();}int xp=correct*10;level=Math.max(level,1+xp/100);save();base("Fase concluída!","Veja seu resultado");String msg=correct==10?"🌟 PERFEITO!":correct>=7?"👏 Excelente!":"💪 Continue praticando!";card(msg+"\n\n🎯 "+correct+"/10 acertos\n⭐ "+score+" pontos\n🪙 "+coins+" moedas\n🔥 Melhor sequência nesta rodada: "+streak,21);add("▶  Jogar novamente",GREEN,v->start(table,timed));add("🗺  Voltar ao mapa",ORANGE,v->map());add("🏠 Início",NAVY,v->home());}

    void surprise(){base("🎁 Tabuada surpresa","Uma missão surpresa apareceu!");MascotView m=new MascotView(this);m.setBackground(gradient(PURPLE,Color.rgb(45,55,125),26));content.addView(m,new LinearLayout.LayoutParams(-1,dp(240)));card("🎁  Chegou a hora da Tabuada Surpresa!\nVocê não sabe qual tabuada vai aparecer.\nPrepare-se para o desafio!",18);add("🚀 Vamos lá!",GREEN,v->start(0,false));}
    void shop(){base("Loja do Mestre","Personagens e acessórios");card("🪙 "+coins+" moedas\nCompre novos companheiros para sua aventura!",18);String[] names={"🦸‍♂️ Herói","🦊 Raposa","🐼 Panda","🤖 Robô","🐲 Dragão","🚀 Astronauta"};int[] prices={0,25,50,75,120,180};for(int i=0;i<names.length;i++){final int ix=i;String key="item_"+prices[i];boolean own=prices[i]==0||p.getBoolean(key,false);Button b=button(names[i]+"   "+(own?"✓ Selecionado":"🪙 "+prices[i]),i%2==0?PURPLE:BLUE);b.setOnClickListener(v->{if(own){avatar=names[ix];save();toast("⭐ Personagem escolhido!");shop();}else if(coins>=prices[ix]){coins-=prices[ix];p.edit().putBoolean(key,true).apply();avatar=names[ix];save();toast("🎉 Personagem desbloqueado!");shop();}else toast("🪙 Faltam "+(prices[ix]-coins)+" moedas.");});content.addView(b);}add("🎨 Personalizar",ORANGE,v->customize());}
    void customize(){base("Personalização","Monte o visual do seu herói");MascotView m=new MascotView(this);m.setBackground(gradient(NAVY,Color.rgb(56,78,125),24));content.addView(m,new LinearLayout.LayoutParams(-1,dp(280)));card("Personagem atual\n"+avatar+"\nEscolha acessórios em futuras atualizações.",18);add("✓ Salvar personalização",GREEN,v->{toast("Personalização salva!");home();});}
    void achievements(){base("Conquistas","Cada pequena vitória conta!");int total=0;for(int i=1;i<=10;i++)total+=p.getInt("best_"+i,0);achievement("🥉","Primeira vitória","Complete uma fase",total>=5);achievement("🔥","Sequência de 10","Acerte 10 questões",total>=10);achievement("⭐","Mestre da Tabuada","Complete todas as tabuadas",total>=50);achievement("🗺","Explorador","Conquiste 3 mundos",total>=30);achievement("⚡","Desafio do tempo","Acerte 10 no modo rápido",p.getInt("timed_best",0)>=10);}
    void achievement(String icon,String title,String desc,boolean ok){LinearLayout c=new LinearLayout(this);c.setGravity(Gravity.CENTER_VERTICAL);c.setPadding(dp(12),dp(8),dp(12),dp(8));c.setBackground(bg(Color.WHITE,20));TextView i=text(icon,30,TEXT);c.addView(i,new LinearLayout.LayoutParams(dp(58),dp(70)));LinearLayout m=new LinearLayout(this);m.setOrientation(LinearLayout.VERTICAL);TextView a=text(title,17,TEXT);a.setTypeface(null,1);m.addView(a);m.addView(text(desc,12,MUTED));c.addView(m,new LinearLayout.LayoutParams(0,dp(70),1));c.addView(text(ok?"✓":"🔒",22,ok?GREEN:MUTED),new LinearLayout.LayoutParams(dp(45),dp(70)));LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(-1,dp(82));lp.setMargins(0,dp(5),0,dp(5));content.addView(c,lp);}
    void progress(){base("Progresso","Acompanhe sua evolução");for(int i=1;i<=10;i++){int b=p.getInt("best_"+i,0);int pct=b*10;TextView t=text("Tabuada "+i+"                         "+pct+"%",15,TEXT);t.setBackground(bg(Color.WHITE,18));content.addView(t,new LinearLayout.LayoutParams(-1,dp(48)));ProgressBar pb=new ProgressBar(this,null,android.R.attr.progressBarStyleHorizontal);pb.setProgress(pct);content.addView(pb,new LinearLayout.LayoutParams(-1,dp(12)));}add("🏠 Início",NAVY,v->home());}
    void daily(){String today=new SimpleDateFormat("yyyyMMdd",Locale.US).format(new Date());String last=p.getString("daily","");base("Recompensa diária","Volte todos os dias para ganhar bônus");card(last.equals(today)?"⏳ Recompensa já recebida hoje!\nVolte amanhã para continuar sua sequência.":"🎁 Baú aberto!\nVocê ganhou 20 moedas hoje.\nContinue jogando todos os dias!",19);if(!last.equals(today)){coins+=20;p.edit().putString("daily",today).apply();save();}add("🏠 Voltar",NAVY,v->home());}
    void parents(){final EditText e=new EditText(this);e.setInputType(2);e.setGravity(Gravity.CENTER);new AlertDialog.Builder(this).setTitle("🔐 Área dos pais").setMessage("Resolva: 7 + 5 = ?").setView(e).setPositiveButton("Entrar",(d,w)->{if("12".equals(e.getText().toString().trim()))parentPanel();else toast("Resposta incorreta.");}).setNegativeButton("Cancelar",null).show();}
    void parentPanel(){base("Área dos pais","Acompanhe o aprendizado");int total=0;for(int i=1;i<=10;i++)total+=p.getInt("best_"+i,0);card("👤 "+child+"\n⭐ Nível "+level+"   🪙 "+coins+"\n🎯 "+total+" acertos registrados",18);section("DESEMPENHO POR TABUADA");for(int i=1;i<=10;i++){int b=p.getInt("best_"+i,0);TextView t=text("Tabuada "+i+"   "+b+"/10   "+stars(b),14,TEXT);t.setBackground(bg(Color.WHITE,16));content.addView(t,new LinearLayout.LayoutParams(-1,dp(44)));}add("📈 Ver gráfico de progresso",BLUE,v->progress());add("⚙ Configurações",Color.DKGRAY,v->settings());}
    void settings(){base("Configurações","Ajuste o jogo para sua família");card("🔊 Som dos efeitos\n🎵 Música de fundo\n🔔 Notificações\n🌎 Idioma: Português",17);add("🧹 Limpar progresso",RED,v->new AlertDialog.Builder(this).setTitle("Limpar progresso?").setMessage("Isso apagará estrelas, moedas e fases registradas.").setNegativeButton("Cancelar",null).setPositiveButton("Limpar",(d,w)->{p.edit().clear().apply();coins=0;level=1;child="Meu Filho";avatar="🦸‍♂️ Herói";home();}).show());add("ℹ Sobre o app",NAVY,v->new AlertDialog.Builder(this).setTitle("Mestre da Tabuada 5.0").setMessage("Jogo educativo offline para praticar multiplicação.\n\nDesenvolvido por Daniel Marques via IA.").setPositiveButton("OK",null).show());}
    void configure(){final EditText e=new EditText(this);e.setText(child);new AlertDialog.Builder(this).setTitle("👤 Perfil").setMessage("Nome da criança").setView(e).setPositiveButton("Salvar",(d,w)->{child=e.getText().toString().trim();if(child.isEmpty())child="Meu Filho";save();home();}).setNegativeButton("Cancelar",null).show();}
    void hideKeyboard(View v){((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(),0);}
    void stop(){if(timer!=null){timer.cancel();timer=null;}}
    void toast(String s){Toast.makeText(this,s,Toast.LENGTH_SHORT).show();}
    @Override public void onBackPressed(){home();}

    class MascotView extends View {Paint paint=new Paint(3);public MascotView(Context c){super(c);paint.setTypeface(Typeface.DEFAULT_BOLD);}
        protected void onDraw(Canvas c){super.onDraw(c);float w=getWidth(),h=getHeight();paint.setStyle(Paint.Style.FILL);paint.setColor(Color.argb(45,255,255,255));c.drawCircle(w*.15f,h*.18f,dp(30),paint);c.drawCircle(w*.84f,h*.24f,dp(42),paint);
            paint.setColor(Color.rgb(255,202,145));c.drawCircle(w*.5f,h*.42f,dp(48),paint);paint.setColor(Color.rgb(83,48,32));c.drawCircle(w*.5f,h*.29f,dp(50),paint);paint.setColor(Color.rgb(255,202,145));c.drawCircle(w*.5f,h*.40f,dp(40),paint);
            paint.setColor(Color.WHITE);c.drawCircle(w*.43f,h*.40f,dp(7),paint);c.drawCircle(w*.57f,h*.40f,dp(7),paint);paint.setColor(Color.rgb(30,35,55));c.drawCircle(w*.43f,h*.40f,dp(3),paint);c.drawCircle(w*.57f,h*.40f,dp(3),paint);
            paint.setColor(Color.rgb(225,80,90));c.drawOval(w*.47f,h*.45f,w*.53f,h*.50f,paint);paint.setColor(Color.rgb(38,99,180));c.drawRoundRect(w*.36f,h*.56f,w*.64f,h*.86f,dp(30),dp(30),paint);
            paint.setColor(GOLD);c.drawCircle(w*.5f,h*.67f,dp(18),paint);paint.setColor(Color.rgb(225,75,82));Path cape=new Path();cape.moveTo(w*.36f,h*.58f);cape.lineTo(w*.17f,h*.86f);cape.lineTo(w*.38f,h*.80f);cape.close();c.drawPath(cape,paint);paint.setColor(Color.WHITE);paint.setTextAlign(Paint.Align.CENTER);paint.setTextSize(dp(15));c.drawText("M",w*.5f,h*.675f,paint);paint.setTextSize(dp(16));paint.setColor(Color.WHITE);c.drawText("MESTRE DA TABUADA",w*.5f,h*.95f,paint);paint.setTextSize(dp(11));paint.setColor(GOLD);c.drawText("APRENDER • JOGAR • CONQUISTAR",w*.5f,h*.99f,paint);}
    }
}
