import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class Controls extends JPanel implements ModelListener {
    private static final int X = 10;
    private static final int Y = 10;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;
    private String[] fonts;
    private JTextField textField;
    private JComboBox<String> fontList;
    private Canvas canvas;
    private JLabel status;
    private ServerAccepter serverAccepter;
    private ClientHandler clientHandler;
    private JButton serverStartButton;
    private JButton clientStartButton;
    private static boolean isServerOn;
    private JLabel addLabel;
    private JButton addRectButton;
    private JButton addOvalButton;
    private JButton addLineButton;
    private JButton addTextButton;
    private JButton setColorButton;
    private JButton moveToFrontButton;
    private JButton moveToBackButton;
    private JButton removeShapeButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton saveImageButton;
    private int modelId;
    private java.util.List<ObjectOutputStream> outputs = new ArrayList<ObjectOutputStream>();

    public Controls(Canvas canvas) {
        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        textField = new JTextField();
        textField.setEnabled(false);
        GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();
        fonts = g.getAvailableFontFamilyNames();
        fontList = new JComboBox<>(fonts);
        fontList.setEnabled(false);
        this.canvas = canvas;

        JPanel firstRow = new JPanel();
        firstRow.setLayout(new BoxLayout(firstRow, BoxLayout.X_AXIS));

        addLabel = new JLabel("Add");
        addRectButton = new JButton("Rect");
        addRectButton.addActionListener(e -> {
            DRectModel model = new DRectModel();
            model.setId(modelId);
            model.setX(X);
            model.setY(Y);
            model.setWidth(WIDTH);
            model.setHeight(HEIGHT);
            canvas.addShape(model);
            textField.setEnabled(false);
            fontList.setEnabled(false);
            sendModelRemote("add", model);
            modelId++;
        });

        addOvalButton = new JButton("Oval");
        addOvalButton.addActionListener(e -> {
            DOvalModel model = new DOvalModel();
            model.setId(modelId);
            model.setX(X);
            model.setY(Y);
            model.setWidth(WIDTH);
            model.setHeight(HEIGHT);
            canvas.addShape(model);
            textField.setEnabled(false);
            fontList.setEnabled(false);
            sendModelRemote("add", model);
            modelId++;
        });

        addLineButton = new JButton("Line");
        addLineButton.addActionListener(e -> {
            DLineModel model = new DLineModel();
            model.setId(modelId);
            model.setUpperLeft(new Point(X, Y));
            model.setLowerRight(new Point(X + WIDTH, Y + HEIGHT));
            canvas.addShape(model);
            textField.setEnabled(false);
            fontList.setEnabled(false);
            sendModelRemote("add", model);
            modelId++;
        });

        addTextButton = new JButton("Text");
        addTextButton.addActionListener(e -> {
            DTextModel model = new DTextModel();
            model.setId(modelId);
            model.setX(X);
            model.setY(Y);
            model.setWidth(2 * WIDTH);
            model.setHeight(HEIGHT);
            model.attachTextField(textField);
            model.attachFontList(fontList);
            canvas.addShape(model);
            textField.setEnabled(false);
            fontList.setEnabled(false);
            sendModelRemote("add", model);
            modelId++;
        });

        firstRow.add(addLabel);
        firstRow.add(addRectButton);
        firstRow.add(addOvalButton);
        firstRow.add(addLineButton);
        firstRow.add(addTextButton);

        JPanel secondRow = new JPanel();
        secondRow.setLayout(new BoxLayout(secondRow, BoxLayout.X_AXIS));
        setColorButton = new JButton("Set Color");
        setColorButton.addActionListener(e -> {
            if (canvas.getSelectedShape()!= null) {
                Color initialColor = canvas.getSelectedShape().getdShapeModel().getColor();
                Color newColor = JColorChooser.showDialog(this, "Select a color", initialColor);
                canvas.getSelectedShape().getdShapeModel().setColor(newColor);
            }
        });
        secondRow.add(setColorButton);

        JPanel thirdRow = new JPanel();
        thirdRow.setLayout(new BoxLayout(thirdRow, BoxLayout.X_AXIS));

        textField.setMaximumSize(new Dimension(50000, 20));
        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                update();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                update();
            }

            public void update() {
                String text = textField.getText();
                if (canvas.getSelectedShape() != null
                        && canvas.getSelectedShape().getdShapeModel() instanceof DTextModel) {
                    ((DTextModel) canvas.getSelectedShape().getdShapeModel()).setText(text);
                }
            }
        });

        fontList.addActionListener(e -> {
            String font = (String) fontList.getSelectedItem();
            if (canvas.getSelectedShape().getdShapeModel() instanceof DTextModel) {
                ((DTextModel) canvas.getSelectedShape().getdShapeModel()).setFont(font);
            }
        });
        thirdRow.add(textField);
        thirdRow.add(fontList);

        JPanel fourthRow = new JPanel();
        fourthRow.setLayout(new BoxLayout(fourthRow, BoxLayout.X_AXIS));
        moveToFrontButton = new JButton("Move To Front");
        moveToFrontButton.addActionListener(e -> {
            DShape shapeToBeFront = canvas.moveFront();
            if (shapeToBeFront != null) {
                sendModelRemote("front", shapeToBeFront.getdShapeModel());
            }
        });

        moveToBackButton = new JButton("Move To Back");
        moveToBackButton.addActionListener(e -> {
            DShape shapeToBeBack = canvas.moveBack();
            if (shapeToBeBack != null) {
                sendModelRemote("back", shapeToBeBack.getdShapeModel());
            }
        });

        removeShapeButton = new JButton("Remove Shape");
        removeShapeButton.addActionListener(e -> {
            DShape shapeRemoved = canvas.removeShape();
            if (shapeRemoved != null) {
                sendModelRemote("remove", shapeRemoved.getdShapeModel());
            }
        });
        fourthRow.add(moveToFrontButton);
        fourthRow.add(moveToBackButton);
        fourthRow.add(removeShapeButton);

        JPanel fifthRow = new JPanel();
        fifthRow.setLayout(new BoxLayout(fifthRow, BoxLayout.X_AXIS));
        saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int returnValue = chooser.showSaveDialog(null);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = new File(chooser.getSelectedFile().getPath() + ".xml");
                save(file);
            }
        });

        loadButton = new JButton("Load");
        loadButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int returnValue = chooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File fileToBeOpened = chooser.getSelectedFile();
                open(fileToBeOpened);
            }
        });

        saveImageButton = new JButton("Save Image");
        saveImageButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int returnValue = chooser.showSaveDialog(null);
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File file = new File(chooser.getSelectedFile().getPath() + ".png");
                canvas.saveImage(file);
            }
        });
        fifthRow.add(saveButton);
        fifthRow.add(loadButton);
        fifthRow.add(saveImageButton);


        JPanel sixthRow = new JPanel();
        sixthRow.setLayout(new BoxLayout(sixthRow, BoxLayout.X_AXIS));
        status = new JLabel();
        serverStartButton = new JButton("Server Start");
        clientStartButton = new JButton("Client Start");

        serverStartButton.addActionListener(e -> {
            doServer();
        });

        clientStartButton.addActionListener(e -> {
            doClient();
        });

        sixthRow.add(serverStartButton);
        sixthRow.add(clientStartButton);
        sixthRow.add(status);

        TableModel tableModel = new TableModel(canvas);
        JTable tableUI = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(tableUI);

        add(firstRow);
        add(secondRow);
        add(thirdRow);
        add(fourthRow);
        add(fifthRow);
        add(sixthRow);
        add(scrollPane);
        setVisible(true);
    }

    public void save(File file) {
        try {
            XMLEncoder xmlOut = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(file)));
            DShapeModel[] shapeArray = new DShapeModel[canvas.getShapes().size()];
            for (int i = 0; i < canvas.getShapes().size(); i++) {
                shapeArray[i] = canvas.getShapes().get(i).getdShapeModel();
            }
            xmlOut.writeObject(shapeArray);
            xmlOut.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void open(File file) {
        DShapeModel[] shapeArray = null;
        try {
            XMLDecoder xmlIn = new XMLDecoder(new BufferedInputStream(new FileInputStream(file)));
            shapeArray = (DShapeModel[]) xmlIn.readObject();
            xmlIn.close();
            modelId = shapeArray.length;
            canvas.clearAll();

            for (DShapeModel shapeModel : shapeArray) {
                sendModelRemote("removeAll", shapeModel);
            }

            for(DShapeModel shapeModel : shapeArray) {
                if (shapeModel instanceof DTextModel) {
                    ((DTextModel) shapeModel).attachTextField(textField);
                    ((DTextModel) shapeModel).attachFontList(fontList);
                }
                canvas.addShape(shapeModel);
                sendModelRemote("add", shapeModel);
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void addOutput(ObjectOutputStream out) {
        outputs.add(out);
    }

    public void sendModelRemote(String command, DShapeModel model) {
        OutputStream memStream = new ByteArrayOutputStream();
        XMLEncoder encoder = new XMLEncoder(memStream);
        encoder.writeObject(model);
        encoder.close();
        String xmlString = memStream.toString();

        Iterator<ObjectOutputStream> it = outputs.iterator();
        while (it.hasNext()) {
            ObjectOutputStream out = it.next();
            try {
                out.writeObject(command);
                out.writeObject(xmlString);
                out.flush();
            }
            catch (Exception ex) {
                ex.printStackTrace();
                it.remove();
            }
        }
    }

    @Override
    public void modelChanged(DShapeModel model) {
        sendModelRemote("change", model);
    }

    class ServerAccepter extends Thread {
        private int port;

        ServerAccepter(int port) {
            this.port = port;
        }

        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(port);
                while (true) {
                    Socket toClient = null;
                    toClient = serverSocket.accept();

                    addOutput(new ObjectOutputStream(toClient.getOutputStream()));

                    for (DShape shape : canvas.getShapes()) {
                        sendModelRemote("removeAll", shape.getdShapeModel());
                    }
                    for (DShape shape : canvas.getShapes()) {
                        sendModelRemote("add", shape.getdShapeModel());
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void doServer() {
        String result = JOptionPane.showInputDialog("Run server on port", "39587");
        if (result != null) {
            isServerOn = true;
            canvas.setNetworkingOn(true);
            serverStartButton.setEnabled(false);
            clientStartButton.setEnabled(false);
            status.setText("Server mode, port " + result);
            canvas.attachModelListenerForServer(this);
            serverAccepter = new ServerAccepter(Integer.parseInt(result.trim()));
            serverAccepter.start();
        }
    }

    public void doClient() {
        String result = JOptionPane.showInputDialog("Connect to host:port", "127.0.0.1:39587");

        if (result != null) {
            if (isServerOn) {
                String[] parts = result.split(":");
                canvas.setNetworkingOn(true);
                addLabel.setEnabled(false);
                addRectButton.setEnabled(false);
                addOvalButton.setEnabled(false);
                addLineButton.setEnabled(false);
                addTextButton.setEnabled(false);
                textField.setEnabled(false);
                fontList.setEnabled(false);
                setColorButton.setEnabled(false);
                moveToFrontButton.setEnabled(false);
                moveToBackButton.setEnabled(false);
                removeShapeButton.setEnabled(false);
                saveButton.setEnabled(false);
                loadButton.setEnabled(false);
                saveImageButton.setEnabled(false);
                serverStartButton.setEnabled(false);
                clientStartButton.setEnabled(false);
                status.setEnabled(false);
                status.setText("Client mode, port " + parts[1].trim());
                clientHandler = new ClientHandler(parts[0].trim(), Integer.parseInt(parts[1].trim()));
                clientHandler.start();
            } else {
                status.setText("Connection failed!");
            }

        }
    }

    private class ClientHandler extends Thread {
        private String name;
        private int port;

        ClientHandler(String name, int port) {
            this.name = name;
            this.port = port;
        }

        public void run() {
            try {
                Socket toServer = new Socket(name, port);
                ObjectInputStream in = new ObjectInputStream(toServer.getInputStream());
                canvas.clearAll();

                while (true) {
                    String command = (String) in.readObject();
                    String xmlString = (String) in.readObject();
                    XMLDecoder decoder = new XMLDecoder(new ByteArrayInputStream(xmlString.getBytes()));
                    DShapeModel encodedModel = (DShapeModel) decoder.readObject();

                    switch (command) {
                        case "add":
                            canvas.addShape(encodedModel);
                            break;
                        case "remove":
                            canvas.removeSpecificShape(encodedModel);
                            break;
                        case "removeAll":
                            canvas.clearAll();
                            break;
                        case "front":
                            canvas.removeSpecificShape(encodedModel);
                            canvas.addShape(encodedModel);
                            break;
                        case "back":
                            canvas.removeSpecificShape(encodedModel);
                            canvas.addFirst(encodedModel);
                            break;
                        case "change":
                            canvas.mimicShape(encodedModel);
                            break;
                    }
                }
            }
            catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
