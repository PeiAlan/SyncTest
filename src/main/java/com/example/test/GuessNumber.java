package com.example.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Date;

public class GuessNumber {
    public static void main(String[] args) {
        Guess guess = new Guess();
        guess.setTitle("猜数字");
        guess.setBounds(60, 100, 300, 200);
    }
}

class Guess extends JFrame implements ActionListener, ItemListener {
    private static final long serialVersionUID = 2786048376582472016L;
    private Date startTime = new Date();
    /**
     * 计时器
     */
    private Timer timer;
    /**
     * 设置限时时间，单位s
     */
    private final Long timeLimit = 10L;
    JLabel label;
    JButton generate, ok;
    JTextField number;
    JRadioButton limit10, limit100;
    int limitNumber, guessNumber = -1;

    public Guess() {
        init();
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    void init() {
        setLayout(new FlowLayout());
        generate = new JButton("得到一个随机数");
        generate.addActionListener(this);
        label = new JLabel("输入猜测");
        limit10 = new JRadioButton("10以内");
        limit10.addItemListener(this);
        limit100 = new JRadioButton("100以内");
        limit100.addItemListener(this);
        number = new JTextField(15);
        ok = new JButton("确定");
        ok.addActionListener(this);
        add(generate);
        add(label);
        ButtonGroup group = new ButtonGroup();
        group.add(limit10);
        group.add(limit100);
        add(limit10);
        add(limit100);
        add(number);
        add(ok);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() == limit10) {
            limitNumber = 10;
        } else {
            limitNumber = 100;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generate) {
            // 再次点击 计时清零，并重新开始计时
            startTime = new Date();
            // 触发生成新的计时器，并且开始计时
            startTimer();
            if (limitNumber != 10 && limitNumber != 100) {
                JOptionPane.showMessageDialog(this, "请先选择随机数范围！", "提示对话框", JOptionPane.WARNING_MESSAGE);
            }
            try {
                guessNumber = (int) (limitNumber * Math.random());
            } catch (Exception x) {
                JOptionPane.showMessageDialog(this, "请先选择随机数范围！", "提示对话框", JOptionPane.WARNING_MESSAGE);
            }
        } else if (e.getSource() == ok) {
            Long useTime = calculateTime(startTime, new Date());
            try {
                if (guessNumber == -1) {
                    JOptionPane.showMessageDialog(this, "请先得到一个随机数！", "提示对话框", JOptionPane.WARNING_MESSAGE);
                }
                if (Integer.parseInt(number.getText()) > guessNumber) {
                    label.setText("猜大了！！" + checkTime(useTime));
                }
                if (Integer.parseInt(number.getText()) < guessNumber) {
                    label.setText("猜小了！！" + checkTime(useTime));
                }
                if (Integer.parseInt(number.getText()) == guessNumber) {
                    label.setText("猜对了！！" + checkTime(useTime));
                }
            } catch (Exception x) {
                JOptionPane.showMessageDialog(this, "输入错误！", "提示对话框", JOptionPane.WARNING_MESSAGE);
            }
        }

    }

    /**
     * 计算时差
     *
     * @param startTime
     * @param endTime
     * @return 秒
     */
    public Long calculateTime(Date startTime, Date endTime) {
        return (endTime.getTime() - startTime.getTime()) / 1000;
    }

    /**
     * 检查时间是否小于 timelimit
     */
    public String checkTime(Long useTime) {
        if (useTime <= timeLimit) {
            return "耗时" + useTime + "秒";
        } else {
            return "超时了！";
        }
    }

    /**
     * 循环检查是否超时
     */
    public void startTimer() {
        if (timer != null && timer.isRunning()) {
            timer.stop();
        } else {
            timer = null;
        }
        timer = new Timer(100, e -> {
            Long useTime = calculateTime(startTime, new Date());
            if (useTime > timeLimit) {
                JOptionPane.showMessageDialog(this, "超时了！你需要重新获取一个随机数！！", "超时提示框", JOptionPane.ERROR_MESSAGE);
                //结束计时校验
                timer.stop();
            }
        });
        timer.start();
    }

}

