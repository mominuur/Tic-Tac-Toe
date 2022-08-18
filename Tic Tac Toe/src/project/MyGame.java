package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Date;

public class MyGame extends JFrame implements ActionListener {
    JLabel heading,clockLabel;
    Font font = new Font("",Font.BOLD,40);
    JPanel mainPanel;


    JButton[] btns = new JButton[9];

    int [] gameChances = {2,2,2,2,2,2,2,2,2};
    int activePlayer = 0;

    int[][] wins = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    int winner;

    boolean gameOver = false;

    MyGame(){
        System.out.println("hello");
        setTitle("Tic Tac Toe Game");
        setSize(850,850);
        ImageIcon icon = new ImageIcon("src/img/icon.png");
        setIconImage(icon.getImage());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setVisible(true);
    }
    private void createGUI(){

        this.getContentPane().setBackground(Color.decode("#2196f3"));
        this.setLayout(new BorderLayout());
        // north section heading
        // south clock
        // east and west blank
        // middle grid of 3x3

        //creating heading
        heading = new JLabel("Lucky");
        heading.setFont(font);
        heading.setForeground(Color.WHITE);
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(heading,BorderLayout.NORTH);

        //creating object of JLabel for clock

         clockLabel = new JLabel("clock");
         clockLabel.setFont(font);
         clockLabel.setForeground(Color.WHITE);
         clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
         this.add(clockLabel,BorderLayout.SOUTH);

         Thread t = new Thread(){
             public void run(){
                 try{
                     while(true){
                         String dateTime =  DateFormat.getTimeInstance(DateFormat.MEDIUM).format(new Date());
                         clockLabel.setText(dateTime);
                         Thread.sleep(1000);
                     }
                 }
                 catch(Exception e){
                     e.printStackTrace();
                 }
             }
         };
         t.start();

         // panel section;
        mainPanel = new JPanel();

        mainPanel.setLayout(new GridLayout(3,3));

        for(int i = 1; i<=9; i++){
            JButton btn = new JButton();
            btn.setBackground(Color.BLACK);
            btn.setFont(font);
            btns[i-1] = btn;
            btn.addActionListener(this);
            btn.setName(String.valueOf(i-1));
            mainPanel.add(btn);
        }
        this.add(mainPanel,BorderLayout.CENTER);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton currentButton = (JButton) e.getSource();
        String namestr = currentButton.getName();
        int name = Integer.parseInt(namestr.trim());

        if(gameOver)
            return;

        if(gameChances[name]==2){
            if(activePlayer==1){
                currentButton.setIcon(new ImageIcon("src/img/1.png"));
                gameChances[name] = 1;
                activePlayer = 0;
            }
            else{
                currentButton.setIcon(new ImageIcon("src/img/0.png"));
                gameChances[name] = 0;
                activePlayer = 1;
            }
        }else{
            JOptionPane.showMessageDialog(this,"Position already occupied");
        }
        for(int[] temp:wins){
            if(gameChances[temp[0]]==gameChances[temp[1]] && gameChances[temp[1]]==gameChances[temp[2]]
                    && gameChances[temp[0]]!=2){
                gameOver = true;
                winner = gameChances[temp[0]];
                JOptionPane.showMessageDialog(this, "player "+winner+" has won the game");
                int i = JOptionPane.showConfirmDialog(this,"Do you want to play again ?");

                if(i==0){
                    this.setVisible(false);
                    new MyGame();
                }else if(i==1){
                    System.exit(0);
                }
                break;

            }
        }
        int c =0;
        for(int x:gameChances){
            if(x==2)
                c++;
        }
        if(c==0&&gameOver==false){
            JOptionPane.showMessageDialog(this,"It's a draw");
            int i = JOptionPane.showConfirmDialog(this,"Do you want to play again ?");

            if(i==0){
                this.setVisible(false);
                new MyGame();
            }else if(i==1){
                System.exit(0);
            }
        }

    }
}
