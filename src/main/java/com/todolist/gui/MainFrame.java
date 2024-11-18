package com.todolist.gui;
import com.todolist.config.UserSession;
import com.todolist.controller.UserController;
import com.todolist.entity.User;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private final JTabbedPane tabbedPane;//主标签页

    private final JPanel mainPanel;
    private final JPanel taskManagementPanel;
    private final JPanel calendarPanel;
    private final JPanel timelinePanel;
    private final JPanel userPanel;



    public MainFrame() {
        setTitle("任务管理应用");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 创建主标签页
        tabbedPane = new JTabbedPane();

        // 创建主界面标签
        mainPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("主界面", mainPanel);
        // 进一步分割主界面标签
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, createLeftPanel(), createRightPanel());
        splitPane.setDividerLocation(400); // 设置分隔条位置
        mainPanel.add(splitPane, BorderLayout.CENTER);

        // 创建任务管理页面标签
        taskManagementPanel = new JPanel(new BorderLayout());
        taskManagementPanel.add(createTaskManagePanel(),BorderLayout.CENTER);
        tabbedPane.addTab("任务管理", taskManagementPanel);

        // 创建日历界面标签
        calendarPanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("日历", calendarPanel);

        // 创建任务时间轴界面标签
        timelinePanel = new JPanel(new BorderLayout());
        tabbedPane.addTab("时间轴", timelinePanel);

        // 创建用户界面标签
        userPanel = new JPanel(new BorderLayout());
        userPanel.add(createLoginPanel(), BorderLayout.PAGE_START); // 添加登录面板到用户设置面板
        tabbedPane.addTab("用户设置", userPanel);

        // 添加标签页到主窗口
        add(tabbedPane, BorderLayout.CENTER);

        // 显示窗口
        setVisible(true);
    }

    private JPanel createTaskManagePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 设置组件间的间隔
        gbc.fill = GridBagConstraints.HORIZONTAL; // 组件水平填充

        return panel;
    }

    //登录界面
    private JPanel createLoginPanel() {

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 设置组件间的间隔
        gbc.fill = GridBagConstraints.HORIZONTAL; // 组件水平填充


        JLabel userLabel = new JLabel("用户名:");
        JTextField userTextField = new JTextField(15);
        JLabel passwordLabel = new JLabel("密码:");
        JPasswordField passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("登录");

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(userLabel, gbc);

        gbc.gridx = 1;
        panel.add(userTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // 让登录按钮跨越两列
        gbc.fill = GridBagConstraints.NONE; // 按钮不填充
        panel.add(loginButton, gbc);


        // 为登录按钮添加事件监听器
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userTextField.getText();
                String password = new String(passwordField.getPassword());
                // 这里添加验证用户名和密码的逻辑
                if (UserController.userLogin(username,password)) {
                    //JOptionPane.showMessageDialog(null, "登录成功！");
                    // 登录成功后的逻辑
                    updateUserInfoPanel(username, 1); // 更新用户信息面板
                } else {
                    JOptionPane.showMessageDialog(null, "用户名或密码错误！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return panel;
    }

    //登陆后重写user界面
    private void updateUserInfoPanel(String username, int userId) {
        // 移除登录面板
        userPanel.removeAll();

        // 创建并添加用户信息显示面板
        JPanel userInfoPanel = new JPanel(new GridBagLayout());
        // 创建 GridBagConstraints 对象
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // 设置组件间的间隔
        gbc.fill = GridBagConstraints.HORIZONTAL; // 组件水平填充

        User user =  UserSession.getInstance().getCurrentUser();

        //指定位置 0行0列
        gbc.gridx = 0;
        gbc.gridy = 0;
        userInfoPanel.add(new JLabel("已登录" ), gbc);

        gbc.gridy = 2;
        userInfoPanel.add(new JLabel("用户名："+user.getUsername()), gbc);

        gbc.gridy = 4;
        userInfoPanel.add(new JLabel("用户邮箱: "+ user.getEmail() ),gbc);

        // 重新设置用户设置面板
        userPanel.add(userInfoPanel, BorderLayout.PAGE_START);
        userPanel.revalidate(); // 重新验证面板
        userPanel.repaint(); // 重绘面板
    }

    private JPanel createLeftPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("今日任务"));
        return panel;
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel();
        panel.add(new JLabel("ddl任务"));
        return panel;
    }


}
