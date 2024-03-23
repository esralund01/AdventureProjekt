// Setup draggable er kraftigt inspireret af: https://stackoverflow.com/questions/16046824/making-a-java-swing-frame-movable-and-setundecorated

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Window {

    private final UserInterface ui;
    private final Adventure adventure;
    private final JFrame frame;
    private Point mouseDownCompCoords; // Setup draggable
    private final ActionListener actionListener;
    private final JCheckBox ls;
    private final JProgressBar playerHealthBar;
    private final JLabel playerHP;
    private final JPanel ehPanel;
    private final JButton north;
    private final JButton east;
    private final JButton west;
    private final JButton south;
    private final JButton simpleAttack;
    private final JButton xyzzy;
    private final JPanel attackPanel;
    private final JPanel consumePanel;
    private final JPanel dropPanel;
    private final JPanel equipPanel;
    private final JPanel takePanel;

    public Window(UserInterface ui, Adventure adventure) {
        this.ui = ui;
        this.adventure = adventure;

        // Setup window
        frame = new JFrame();
        frame.setLayout(new GridLayout(11, 1));
        frame.setUndecorated(true);
        frame.setLocationRelativeTo(null);
        frame.setFocusable(true);

        // Setup draggable
        MouseAdapter frameDragListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                mouseDownCompCoords = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                mouseDownCompCoords = null;
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                Point currCoords = e.getLocationOnScreen();
                frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
            }
        };
        frame.addMouseListener(frameDragListener);
        frame.addMouseMotionListener(frameDragListener);

        // Setup key handler
        frame.addKeyListener(new KeyListener() {
            @Override public void keyTyped(KeyEvent e) {}

            @Override
            public void keyPressed(KeyEvent e) {
                boolean update = true;
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ESCAPE -> {
                        update = false;
                        hideWindow();
                    }
                    case KeyEvent.VK_UP, KeyEvent.VK_N -> {
                        ui.go("n");
                        if (!north.isEnabled()) {
                            update = false;
                            System.out.println();
                        }
                    }
                    case KeyEvent.VK_RIGHT, KeyEvent.VK_E -> {
                        ui.go("e");
                        if (!east.isEnabled()) {
                            update = false;
                            System.out.println();
                        }
                    }
                    case KeyEvent.VK_LEFT, KeyEvent.VK_W -> {
                        ui.go("w");
                        if (!west.isEnabled()) {
                            update = false;
                            System.out.println();
                        }
                    }
                    case KeyEvent.VK_DOWN, KeyEvent.VK_S -> {
                        ui.go("s");
                        if (!south.isEnabled()) {
                            update = false;
                            System.out.println();
                        }
                    }
                    case KeyEvent.VK_A -> {
                        ui.attack("");
                        if (!simpleAttack.isEnabled()) {
                            update = false;
                            System.out.println();
                        }
                    }
                    case KeyEvent.VK_X -> {
                        ui.xyzzy();
                        if (!xyzzy.isEnabled()) {
                            update = false;
                            System.out.println();
                        }
                    }
                    case KeyEvent.VK_L -> {
                        ui.turnLight(adventure.getCurrentRoom().getIsDark());
                        if (!ls.isEnabled()) {
                            update = false;
                            System.out.println();
                        }
                    }
                    case KeyEvent.VK_Q -> ui.exit();
                    case KeyEvent.VK_SPACE -> ui.health();
                    case KeyEvent.VK_H -> ui.help();
                    case KeyEvent.VK_I -> ui.inventory();
                    case KeyEvent.VK_ENTER -> ui.look();
                    default -> update = false;
                }
                if (update) {
                    update(true);
                }
            }

            @Override public void keyReleased(KeyEvent e) {}
        });

        // Setup button handler
        actionListener = e -> {
            boolean update = true;
            switch (e.getActionCommand()) {
                case "X" -> {
                    update = false;
                    hideWindow();
                }
                case "▲" -> ui.go("n");
                case "▶" -> ui.go("e");
                case "◀" -> ui.go("w");
                case "▼" -> ui.go("s");
                case "attack" -> ui.attack("");
                case "exit" -> ui.exit();
                case "health" -> ui.health();
                case "help" -> ui.help();
                case "inventory" -> ui.inventory();
                case "light" -> ui.turnLight(adventure.getCurrentRoom().getIsDark());
                case "look" -> ui.look();
                case "xyzzy" -> ui.xyzzy();
                case String s when s.startsWith("attack ") -> ui.attack(s.substring(7));
                case String s when s.startsWith("drink ") -> ui.consume(false, s.substring(6));
                case String s when s.startsWith("drop ") -> ui.drop(s.substring(5));
                case String s when s.startsWith("eat ") -> ui.consume(true, s.substring(4));
                case String s when s.startsWith("equip ") -> ui.equip(s.substring(6));
                case String s when s.startsWith("take ") -> ui.take(s.substring(5));
                default -> {
                    update = false;
                    System.out.println(e.getActionCommand());
                }
            }
            if (update) {
                update(true);
            }
        };

        // Setup esc-button
        JPanel escPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        frame.getContentPane().add(escPanel);
        JButton esc = createButton("X", escPanel, "ESCAPE");
        esc.setForeground(Color.WHITE);
        esc.setBackground(Color.RED);

        // Setup player health bar
        JPanel phPanel = new JPanel();
        frame.getContentPane().add(phPanel);
        phPanel.add(new JLabel("Player health:"));
        playerHealthBar = new JProgressBar(0, adventure.getMaxHealth());
        playerHealthBar.setBorderPainted(false);
        playerHealthBar.setBackground(Color.LIGHT_GRAY);
        phPanel.add(playerHealthBar);
        playerHP = new JLabel();
        phPanel.add(playerHP);

        // Setup enemy health bar
        ehPanel = new JPanel();
        frame.getContentPane().add(ehPanel);

        // Setup go north command
        JPanel gnPanel = new JPanel();
        frame.getContentPane().add(gnPanel);
        north = createButton("▲", gnPanel, "go north");

        // Setup other go commands
        JPanel goPanel = new JPanel();
        frame.getContentPane().add(goPanel);
        west = createButton("◀", goPanel, "go west");
        south = createButton("▼", goPanel, "go south");
        east = createButton("▶", goPanel, "go east");

        // Setup simple commands
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel);
        simpleAttack = createButton("attack", panel, "A");
        String[] buttons = {"exit", "health", "help", "inventory", "look"};
        String[] tps = {"Q", "SPACE", "H", "I", "ENTER"};
        for (int i = 0; i < buttons.length; i++) {
            createButton(buttons[i], panel, tps[i]);
        }
        xyzzy = createButton("xyzzy", panel, "<html>X<br>Teleports you to the last place you teleported from.</html>");
        ls = new JCheckBox("light");
        ls.setToolTipText("L");
        ls.addActionListener(actionListener);
        ls.setFocusable(false);
        panel.add(ls);

        // Setup commands with selectables
        attackPanel = new JPanel();
        frame.getContentPane().add(attackPanel);
        consumePanel = new JPanel();
        frame.getContentPane().add(consumePanel);
        dropPanel = new JPanel();
        frame.getContentPane().add(dropPanel);
        equipPanel = new JPanel();
        frame.getContentPane().add(equipPanel);
        takePanel = new JPanel();
        frame.getContentPane().add(takePanel);
    }

    public void showWindow() {
        update(false);
        frame.setVisible(true);
    }

    private void hideWindow() {
        frame.setVisible(false);
        ui.startProgram();
    }

    public void killWindow() {
        frame.dispose();
    }

    private JButton createButton(String name, JPanel panel, String toolTip) {
        JButton b = new JButton(name);
        b.addActionListener(actionListener);
        b.setFocusable(false);
        if (!toolTip.isEmpty()) {
            b.setToolTipText(toolTip);
        }
        panel.add(b);
        return b;
    }

    private JButton createButtonWithGeneratedToolTip(String command, JPanel panel, Object object) {
        String shortName;
        String toolTip = "<html>";
        switch (object) {
            case Enemy e -> {
                shortName = e.getShortName();
                toolTip += command + " " + removeYellow(e) + "<br>HP health: " + e.getHealth() + "<br>HP damage: " + e.getEquipped().getHitPoints();
                if (e.getEquipped() instanceof RangedWeapon r) {
                    toolTip += "<br>Ammo left: " + r.numberOfProjectiles;
                }
            }
            case RangedWeapon r -> {
                shortName = r.getShortName().toLowerCase();
                toolTip += command + " " + removeYellow(r) + "<br>HP damage: " + r.getHitPoints() + "<br>Ammo left: " + r.numberOfProjectiles;
            }
            case MeleeWeapon m -> {
                shortName = m.getShortName().toLowerCase();
                toolTip += command + " " + removeYellow(m) + "<br>HP damage: " + m.getHitPoints();
            }
            case Consumable c -> {
                shortName = c.getShortName().toLowerCase();
                toolTip += command + " " + removeYellow(c);
                if (c.getHitPoints() < 0) {
                    toolTip += "<br>HP damage: " + c.getHitPoints() * -1;
                } else {
                    toolTip += "<br>HP restore: " + c.getHitPoints();
                }
            }
            case Item i -> {
                shortName = i.getShortName().toLowerCase();
                toolTip += command + " " + removeYellow(i);
            }
            default -> {
                return null;
            }
        }
        toolTip += "</html>";
        return createButton(command + " " + shortName, panel, toolTip);
    }

    private void update(boolean newLine) {
        if (newLine) {
            System.out.println();
        }

        // Update player health bar
        updateHealthBar(playerHealthBar, playerHP, adventure.getHealth(), adventure.getMaxHealth());

        // Update enemy health bar
        ehPanel.removeAll();
        if (adventure.getSelected() == null) {
            ehPanel.add(new JLabel("No enemy selected."));
        } else if (adventure.getSelected().getHealth() == 0) {
            ehPanel.add(new JLabel(ui.aToTheUpperCase(removeYellow(adventure.getSelected())) + " has died."));
        } else {
            ehPanel.add(new JLabel("Enemy health:"));
            JProgressBar enemyHealthBar = new JProgressBar(0, adventure.getSelected().getMaxHealth());
            enemyHealthBar.setBorderPainted(false);
            enemyHealthBar.setBackground(Color.LIGHT_GRAY);
            ehPanel.add(enemyHealthBar);
            JLabel enemyHP = new JLabel();
            ehPanel.add(enemyHP);
            updateHealthBar(enemyHealthBar, enemyHP, adventure.getSelected().getHealth(), adventure.getSelected().getMaxHealth());
        }

        // Update go
        if (adventure.getCurrentRoom().getIsDark()) {
            north.setEnabled(adventure.getCurrentRoom().getNorth() == adventure.getPreviousRoom());
            east.setEnabled(adventure.getCurrentRoom().getEast() == adventure.getPreviousRoom());
            west.setEnabled(adventure.getCurrentRoom().getWest() == adventure.getPreviousRoom());
            south.setEnabled(adventure.getCurrentRoom().getSouth() == adventure.getPreviousRoom());
        } else {
            north.setEnabled(adventure.getCurrentRoom().getNorth() != null);
            east.setEnabled(adventure.getCurrentRoom().getEast() != null);
            west.setEnabled(adventure.getCurrentRoom().getWest() != null);
            south.setEnabled(adventure.getCurrentRoom().getSouth() != null);
        }

        // Update simple commands
        updateAttackButton(simpleAttack);
        xyzzy.setEnabled(adventure.getCurrentRoom() != adventure.getPortalRoom());
        ls.setSelected(!adventure.getCurrentRoom().getIsDark());
        ls.setEnabled(adventure.getCurrentRoom().hasLights);

        // Update attack
        attackPanel.removeAll();
        if (adventure.getCurrentRoom().enemies.isEmpty()) {
            attackPanel.add(new JLabel("No enemies to attack here."));
        } else {
            for (Enemy enemy : adventure.getCurrentRoom().enemies) {
                JButton b = createButtonWithGeneratedToolTip("attack", attackPanel, enemy);
                updateAttackButton(b);
                if (adventure.getCurrentRoom().getIsDark()) {
                    b.setEnabled(false);
                }
            }
        }

        // Update consume
        consumePanel.removeAll();
        ArrayList<Item> inventoryAndRoom = new ArrayList<>(adventure.getCurrentRoom().items);
        inventoryAndRoom.addAll(adventure.getInventory());
        boolean nothingToConsume = true;
        for (Item item : inventoryAndRoom) {
            if (item instanceof Consumable) {
                createButtonWithGeneratedToolTip(((item instanceof Food) ? "eat" : "drink"), consumePanel, item);
                nothingToConsume = false;
            }
        }
        if (nothingToConsume) {
            consumePanel.add(new JLabel("There's nothing to eat or drink."));
        }

        // Update drop
        dropPanel.removeAll();
        if (adventure.getInventory().isEmpty()) {
            dropPanel.add(new JLabel("Inventory is empty."));
        } else {
            for (Item item : adventure.getInventory()) {
                createButtonWithGeneratedToolTip("drop", dropPanel, item);
            }
        }

        // Update equip
        equipPanel.removeAll();
        boolean noWeapons = true;
        for (Item item : adventure.getInventory()) {
            if (item instanceof Weapon) {
                JButton b = createButtonWithGeneratedToolTip("equip", equipPanel, item);
                if (item == adventure.getEquipped()) {
                    b.setEnabled(false);
                }
                noWeapons = false;
            }
        }
        if (noWeapons) {
            equipPanel.add(new JLabel("Inventory has no weapons to equip."));
        }

        // Update take
        takePanel.removeAll();
        ArrayList<Item> enemiesEquipped = new ArrayList<>();
        for (Enemy enemy : adventure.getCurrentRoom().enemies) {
            enemiesEquipped.add(enemy.getEquipped());
        }
        if (adventure.getCurrentRoom().items.isEmpty() && enemiesEquipped.isEmpty()) {
            takePanel.add(new JLabel("No items to take here."));
        } else {
            for (Item item : adventure.getCurrentRoom().items) {
                JButton b = createButtonWithGeneratedToolTip("take", takePanel, item);
                if (adventure.getCurrentRoom().getIsDark()) {
                    b.setEnabled(false);
                }
            }
            for (Item item : enemiesEquipped) {
                JButton b = createButtonWithGeneratedToolTip("take", takePanel, item);
                b.setEnabled(false);
            }
        }

        // Update window
        frame.pack();
        frame.update(frame.getGraphics());
    }

    private void updateAttackButton(JButton b) {
        if (adventure.getEquipped() != null) {
            b.setEnabled(adventure.getEquipped().canUse());
        } else {
            b.setEnabled(false);
        }
    }

    private void updateHealthBar(JProgressBar pb, JLabel hp, int health, int maxHealth) {
        pb.setValue(health);
        int healthPercentage = health * 100 / maxHealth;
        if (healthPercentage > 66) {
            pb.setForeground(Color.GREEN);
        } else if (healthPercentage > 33) {
            pb.setForeground(Color.YELLOW);
        } else {
            pb.setForeground(Color.RED);
        }
        hp.setText(health + " / " + maxHealth + " HP");
    }

    private String removeYellow(Object object) {
        int i = 0;
        String s = "";
        if (object instanceof Item item) {
            i = 2;
            s = item.getLongName();
        } else if (object instanceof Enemy enemy) {
            s = enemy.getLongName();
        } else {
            return s;
        }
        for (int j = i; j < 4; j++) {
            int m = j % 2;
            int removeFrom = s.indexOf('\u001B');
            if (removeFrom != -1) {
                s = s.substring(0, removeFrom) + s.substring(removeFrom + 5 - m);
            }
        }
        return s;
    }
}