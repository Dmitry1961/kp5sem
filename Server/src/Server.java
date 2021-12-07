import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.sql.*;

public class Server {
    static int p = 0;
    private static Statement statement;

    public static void main(String[] args) throws IOException {
        try {
            ServerSocket server = new ServerSocket(12348);
            System.out.println("Server started!");
            while (true) {
                Socket accept1 = server.accept();
                new Thread(() -> {
                    try (Socket socket = accept1) {
                        serv(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    private static void serv(final Socket socket) throws IOException, NullPointerException, SocketException {
        String usernamee;
        String login;
        String password;
        String idUser;
        String idCar;
        String brand;
        String year;
        String volume;
        String engine;
        String priceDay;
        String idHouse;
        String country;
        String type;
        String countRoom;
        String festive;
        String userName;
        String price;
        String datee;
        String nameRent;
        String prosrosh;
        String idIncome;
        try (BufferedWriter writer =
                     new BufferedWriter(
                             new OutputStreamWriter(
                                     socket.getOutputStream()));
             BufferedReader reader =
                     new BufferedReader(
                             new InputStreamReader(
                                     socket.getInputStream()))) {
            String url = "jdbc:mysql://localhost:3306/kosten";
            String username = "root";

            String pasword = "coffeewizard";//поменять

            Class.forName("com.mysql.cj.jdbc.Driver");
            while (true) {
                if (!socket.isClosed()) {
                    String request = reader.readLine();
                    if (request == null) {

                    } else {
                        switch (request) {
                            case ("CreateBd") -> {
                                String nam = "";
                                try (Connection connection = DriverManager.getConnection(url, username, pasword)) {
                                    ResultSet resultSet = connection.getMetaData().getCatalogs();
                                    while (resultSet.next()) {
                                        String databaseName = resultSet.getString(1);
                                        if (databaseName.equals("kosten")) {
                                            nam = databaseName;
                                            break;
                                        } else {
                                            nam = databaseName;
                                        }
                                    }
                                    resultSet.close();
                                    if (nam.equals("kosten")) {

                                    } else {
                                        String ss = "CREATE DATABASE `kosten` /*!40100 DEFAULT" +
                                                " CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;\n";
                                        try {
                                            PreparedStatement DB = DriverManager.getConnection(url, username, pasword).prepareStatement(ss);
                                            DB.executeUpdate(); // заакинуть в бд
                                            System.out.println("База данных создана");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        String qq = "CREATE TABLE `car` (\n" +
                                                "  `idCar` int NOT NULL AUTO_INCREMENT,\n" +
                                                "  `brand` varchar(45) NOT NULL,\n" +
                                                "  `year` varchar(45) NOT NULL,\n" +
                                                "  `volume` varchar(45) NOT NULL,\n" +
                                                "  `engine` varchar(45) NOT NULL,\n" +
                                                "  `priceDay` varchar(45) NOT NULL,\n" +
                                                "  PRIMARY KEY (`idCar`)\n" +
                                                ") ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n";
                                        try {
                                            PreparedStatement tableBuy = DriverManager.getConnection(url, username, pasword).prepareStatement(qq);
                                            tableBuy.executeUpdate(); // заакинуть в бд
                                            System.out.println("Таблица car создана");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        String ww = "CREATE TABLE `house` (\n" +
                                                "  `idHouse` int NOT NULL AUTO_INCREMENT,\n" +
                                                "  `country` varchar(45) NOT NULL,\n" +
                                                "  `type` varchar(45) NOT NULL,\n" +
                                                "  `countRoom` varchar(45) NOT NULL,\n" +
                                                "  `festive` varchar(45) NOT NULL,\n" +
                                                "  `priceDay` varchar(45) NOT NULL,\n" +
                                                "  PRIMARY KEY (`idHouse`)\n" +
                                                ") ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n";
                                        try {
                                            PreparedStatement tableExpress = DriverManager.getConnection(url, username, pasword).prepareStatement(ww);
                                            tableExpress.executeUpdate(); // заакинуть в бд
                                            System.out.println("Таблица house создана");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        String ee = "CREATE TABLE `income` (\n" +
                                                "  `idIncome` int NOT NULL AUTO_INCREMENT,\n" +
                                                "  `nameRent` varchar(45) NOT NULL,\n" +
                                                "  `price` varchar(45) NOT NULL,\n" +
                                                "  `prosrosh` varchar(45) NOT NULL,\n" +
                                                "  `datee` date NOT NULL,\n" +
                                                "  `username` varchar(45) NOT NULL,\n" +
                                                "  PRIMARY KEY (`idIncome`)\n" +
                                                ") ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n";
                                        try {
                                            PreparedStatement tableJourney = DriverManager.getConnection(url, username, pasword).prepareStatement(ee);
                                            tableJourney.executeUpdate(); // заакинуть в бд
                                            System.out.println("Таблица income создана");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        String rr = "CREATE TABLE `rentcar` (\n" +
                                                "  `idRentCar` int NOT NULL AUTO_INCREMENT,\n" +
                                                "  `brand` varchar(45) NOT NULL,\n" +
                                                "  `year` varchar(45) NOT NULL,\n" +
                                                "  `priceDay` varchar(45) NOT NULL,\n" +
                                                "  `time` date NOT NULL,\n" +
                                                "  `timeEnd` date NOT NULL,\n" +
                                                "  `userName` varchar(45) NOT NULL,\n" +
                                                "  PRIMARY KEY (`idRentCar`)\n" +
                                                ") ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n";
                                        try {
                                            PreparedStatement tableTrips = DriverManager.getConnection(url, username, pasword).prepareStatement(rr);
                                            tableTrips.executeUpdate(); // заакинуть в бд
                                            System.out.println("Таблица rentcar создана");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        String tt = "CREATE TABLE `renthouse` (\n" +
                                                "  `idRentHouse` int NOT NULL AUTO_INCREMENT,\n" +
                                                "  `country` varchar(45) NOT NULL,\n" +
                                                "  `priceDay` varchar(45) NOT NULL,\n" +
                                                "  `time` date NOT NULL,\n" +
                                                "  `timeEnd` date NOT NULL,\n" +
                                                "  `userName` varchar(45) NOT NULL,\n" +
                                                "  PRIMARY KEY (`idRentHouse`)\n" +
                                                ") ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n";
                                        try {
                                            PreparedStatement tableUsers = DriverManager.getConnection(url, username, pasword).prepareStatement(tt);
                                            tableUsers.executeUpdate(); // заакинуть в бд
                                            System.out.println("Таблица renthouse создана");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                        String cc = "CREATE TABLE `user` (\n" +
                                                "  `idUser` int NOT NULL AUTO_INCREMENT,\n" +
                                                "  `username` varchar(45) NOT NULL,\n" +
                                                "  `login` varchar(45) NOT NULL,\n" +
                                                "  `password` varchar(45) NOT NULL,\n" +
                                                "  PRIMARY KEY (`idUser`)\n" +
                                                ") ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;\n";
                                        try {
                                            PreparedStatement tableUsers = DriverManager.getConnection(url, username, pasword).prepareStatement(cc);
                                            tableUsers.executeUpdate(); // заакинуть в бд
                                            System.out.println("Таблица user создана");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    }
                               }

                            }
                            case ("exit") -> {
                                reader.close();
                                writer.close();
                                socket.close();
                            }
                            case ("account") -> {
                                String req2 = reader.readLine();
                                System.out.println("Авторизовался пользователь: " + req2);
                            }
                            case ("exitaccount") -> {
                                String req2 = reader.readLine();
                                System.out.println("Дисконект пользователя: " + req2);
                            }
                            case ("readUsers") -> {
                                try (Connection connection = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection.createStatement();
                                    String sq_str = "SELECT * FROM user";
                                    ResultSet rs = statement.executeQuery(sq_str);
                                    while (rs.next()) {
                                        String flag = String.valueOf(rs.getRow());
                                        writer.write(flag);
                                        writer.newLine();
                                        writer.flush();
                                        idUser = rs.getString("idUser");
                                        writer.write(idUser);
                                        writer.newLine();
                                        writer.flush();
                                        usernamee = rs.getString("username");
                                        writer.write(usernamee);
                                        writer.newLine();
                                        writer.flush();
                                        login = rs.getString("login");
                                        writer.write(login);
                                        writer.newLine();
                                        writer.flush();
                                        password = rs.getString("password");
                                        writer.write(password);
                                        writer.newLine();
                                        writer.flush();
                                    }
                                }
                                String flag = String.valueOf(0);
                                writer.write(flag);
                                writer.newLine();
                                writer.flush();
                                System.out.println("Произошел вывод пользователей от лица админа");
                            }
                            case ("deleteUser") -> {
                                int ID = Integer.parseInt(reader.readLine());
                                String a;
                                try (Connection connection3 = DriverManager.getConnection(url, username, pasword)) {
                                    if (ID == 1) {
                                        System.out.println("Админа удалить нельзя");
                                        a = "1";
                                        writer.write(a);
                                        writer.newLine();
                                        writer.flush();
                                    } else {
                                        a = "0";
                                        writer.write(a);
                                        writer.newLine();
                                        writer.flush();
                                        Statement statement = connection3.createStatement();
                                        statement.executeUpdate("DELETE FROM user WHERE idUser = " + ID);
                                    }
                                } catch (SQLException e) {
                                    writer.write(e.getMessage());
                                    writer.newLine();
                                    writer.flush();
                                }
                                System.out.println("Удаления пользователя с ID = " + ID);
                            }
                            case ("editUsername") -> {
                                String a;
                                String requestType = reader.readLine();
                                int cellType = Integer.parseInt(reader.readLine());
                                if (cellType == 1) {
                                    System.out.println("Админа редактировать нельзя");
                                    a = "1";
                                    writer.write(a);
                                    writer.newLine();
                                    writer.flush();
                                } else {
                                    a = "0";
                                    writer.write(a);
                                    writer.newLine();
                                    writer.flush();
                                    try (Connection connection2 = DriverManager.getConnection(url, username, pasword)) {
                                        statement = connection2.createStatement();
                                        try {
                                            statement.executeUpdate("UPDATE user SET username = '" + requestType + "' WHERE (idUser = " + cellType + ")");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    } catch (SQLException throwables) {
                                        writer.write(throwables.getMessage());
                                        writer.newLine();
                                        writer.flush();
                                    }
                                }
                                System.out.println("Произошло редактирование ника пользователя с ID= " + cellType);
                            }
                            case ("editLogin") -> {
                                String a;
                                String requestSeason = reader.readLine();
                                int cellSeason = Integer.parseInt(reader.readLine());
                                if (cellSeason == 1) {
                                    System.out.println("Админа редактировать нельзя");
                                    a = "1";
                                    writer.write(a);
                                    writer.newLine();
                                    writer.flush();
                                } else {
                                    a = "0";
                                    writer.write(a);
                                    writer.newLine();
                                    writer.flush();
                                    try (Connection connection2 = DriverManager.getConnection(url, username, pasword)) {
                                        statement = connection2.createStatement();
                                        try {
                                            statement.executeUpdate("UPDATE user SET login = '" + requestSeason + "' WHERE (idUser = " + cellSeason + ")");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    } catch (SQLException throwables) {
                                        writer.write(throwables.getMessage());
                                        writer.newLine();
                                        writer.flush();
                                    }
                                }
                                System.out.println("Произошло редактирование логина пользователя с ID= " + cellSeason);
                            }
                            case ("editPassword") -> {
                                String a;
                                String requestFabric = reader.readLine();
                                int cellFabric = Integer.parseInt(reader.readLine());
                                if (cellFabric == 1) {
                                    System.out.println("Админа редактировать нельзя");
                                    a = "1";
                                    writer.write(a);
                                    writer.newLine();
                                    writer.flush();
                                } else {
                                    a = "0";
                                    writer.write(a);
                                    writer.newLine();
                                    writer.flush();
                                    try (Connection connection2 = DriverManager.getConnection(url, username, pasword)) {
                                        statement = connection2.createStatement();
                                        try {
                                            statement.executeUpdate("UPDATE user SET password = '" + requestFabric + "' WHERE (idUser = " + cellFabric + ")");
                                        } catch (SQLException e) {
                                            e.printStackTrace();
                                        }
                                    } catch (SQLException throwables) {
                                        writer.write(throwables.getMessage());
                                        writer.newLine();
                                        writer.flush();
                                    }
                                }
                                System.out.println("Произошло редактирование пароля пользователя с ID= " + cellFabric);
                            }
                            case ("registration") -> {
                                String request1 = reader.readLine();
                                String request2 = reader.readLine();
                                String request3 = reader.readLine();
                                System.out.println(request1 + " " + request2 + " " + request3);
                                String ss = "INSERT INTO " + "user" + "(" + "username" + ","
                                        + "login" + "," + "password" + ")" +
                                        "VALUES(?,?,?)";
                                try {
                                    PreparedStatement prst = DriverManager.getConnection(url, username, pasword).prepareStatement(ss);
                                    prst.setString(1, request1);
                                    prst.setString(2, request2);
                                    prst.setString(3, request3);
                                    prst.executeUpdate(); // заакинуть в бд
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("Произошла регистрация аккаунта " + request2);
                            }
                            case ("authorization") -> {
                                try (Connection connection = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection.createStatement();
                                    String sq_str = "SELECT * FROM user";
                                    ResultSet rs = statement.executeQuery(sq_str);
                                    while (rs.next()){
                                        String flag = String.valueOf(rs.getRow());
                                        writer.write(flag);
                                        writer.newLine();
                                        writer.flush();
                                        usernamee = rs.getString("username");
                                        writer.write(usernamee);
                                        writer.newLine();
                                        writer.flush();
                                        login = rs.getString("login");
                                        writer.write(login);
                                        writer.newLine();
                                        writer.flush();
                                        password = rs.getString("password");
                                        writer.write(password);
                                        writer.newLine();
                                        writer.flush();
                                    }
                                }
                                String flag = String.valueOf(0);
                                writer.write(flag);
                                writer.newLine();
                                writer.flush();
                            }
                            case ("CarInfo") -> {
                                try (Connection connection = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection.createStatement();
                                    String sq_str = "SELECT * FROM car";
                                    ResultSet rs = statement.executeQuery(sq_str);
                                    while (rs.next()) {
                                        String flag = String.valueOf(rs.getRow());
                                        writer.write(flag);
                                        writer.newLine();
                                        writer.flush();
                                        idCar = rs.getString("idCar");
                                        writer.write(idCar);
                                        writer.newLine();
                                        writer.flush();
                                        brand = rs.getString("brand");
                                        writer.write(brand);
                                        writer.newLine();
                                        writer.flush();
                                        year = rs.getString("year");
                                        writer.write(year);
                                        writer.newLine();
                                        writer.flush();
                                        volume = rs.getString("volume");
                                        writer.write(volume);
                                        writer.newLine();
                                        writer.flush();
                                        engine = rs.getString("engine");
                                        writer.write(engine);
                                        writer.newLine();
                                        writer.flush();
                                        priceDay = rs.getString("priceDay");
                                        writer.write(priceDay);
                                        writer.newLine();
                                        writer.flush();
                                    }
                                }
                                String flag = String.valueOf(0);
                                writer.write(flag);
                                writer.newLine();
                                writer.flush();
                                System.out.println("Произошел вывод всех машин");
                            }
                            case ("addCar") -> {
                                String request1 = reader.readLine();
                                String request2 = reader.readLine();
                                String request3 = reader.readLine();
                                String request4 = reader.readLine();
                                String request5 = reader.readLine();

                                String ss = "INSERT INTO " + "car" + "("
                                        + "brand" + "," + "year" + "," + "volume" +"," + "engine" +"," + "priceDay" +")" +
                                        "VALUES(?,?,?,?,?)";
                                try {
                                    PreparedStatement prst = DriverManager.getConnection(url, username, pasword).prepareStatement(ss);
                                    prst.setString(1, request1);
                                    prst.setString(2, request2);
                                    prst.setString(3, request3);
                                    prst.setString(4, request4);
                                    prst.setString(5, request5);

                                    prst.executeUpdate(); // заакинуть в бд
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("Произошла добавление машины");
                            }
                            case ("editYearCar") -> {
                                String requestFabric = reader.readLine();
                                int cellFabric = Integer.parseInt(reader.readLine());
                                try (Connection connection2 = DriverManager.getConnection(url, username, pasword)) {
                                    statement = connection2.createStatement();
                                    try {
                                        statement.executeUpdate("UPDATE car SET year = '" + requestFabric + "' WHERE (idCar = " + cellFabric + ")");
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                } catch (SQLException throwables) {
                                    writer.write(throwables.getMessage());
                                    writer.newLine();
                                    writer.flush();
                                }
                                System.out.println("Произошло редактирование года выпуска машины с ID= " + cellFabric);
                            }
                            case ("editPriceCar") -> {
                                String requestFabric = reader.readLine();
                                int cellFabric = Integer.parseInt(reader.readLine());
                                try (Connection connection2 = DriverManager.getConnection(url, username, pasword)) {
                                    statement = connection2.createStatement();
                                    try {
                                        statement.executeUpdate("UPDATE car SET priceDay = '" + requestFabric + "' WHERE (idCar = " + cellFabric + ")");
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                } catch (SQLException throwables) {
                                    writer.write(throwables.getMessage());
                                    writer.newLine();
                                    writer.flush();
                                }
                                System.out.println("Произошло редактирование цены машины с ID= " + cellFabric);
                            }
                            case ("deleteCar") -> {
                                int ID = Integer.parseInt(reader.readLine());
                                try (Connection connection3 = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection3.createStatement();
                                    statement.executeUpdate("DELETE FROM car WHERE idCar = " + ID);
                                } catch (SQLException e) {
                                    writer.write(e.getMessage());
                                    writer.newLine();
                                    writer.flush();
                                }
                                System.out.println("Удаления машины с ID = " + ID);
                            }
                            case ("CarPoisk") -> {
                                try (Connection connection = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection.createStatement();
                                    String posik = reader.readLine();
                                    String sq_str = "SELECT * FROM car WHERE (brand = "+"'" + posik+"'" + ")";
                                    ResultSet rs = statement.executeQuery(sq_str);
                                    while (rs.next()) {
                                        String flag = String.valueOf(rs.getRow());
                                        writer.write(flag);
                                        writer.newLine();
                                        writer.flush();
                                        idCar = rs.getString("idCar");
                                        writer.write(idCar);
                                        writer.newLine();
                                        writer.flush();
                                        brand = rs.getString("brand");
                                        writer.write(brand);
                                        writer.newLine();
                                        writer.flush();
                                        year = rs.getString("year");
                                        writer.write(year);
                                        writer.newLine();
                                        writer.flush();
                                        volume = rs.getString("volume");
                                        writer.write(volume);
                                        writer.newLine();
                                        writer.flush();
                                        engine = rs.getString("engine");
                                        writer.write(engine);
                                        writer.newLine();
                                        writer.flush();
                                        priceDay = rs.getString("priceDay");
                                        writer.write(priceDay);
                                        writer.newLine();
                                        writer.flush();
                                    }
                                }
                                String flag = String.valueOf(0);
                                writer.write(flag);
                                writer.newLine();
                                writer.flush();
                                System.out.println("Произошел вывод машины по определнному бренду");
                            }
                            case ("HouseInfo") -> {
                                try (Connection connection = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection.createStatement();
                                    String sq_str = "SELECT * FROM house";
                                    ResultSet rs = statement.executeQuery(sq_str);
                                    while (rs.next()) {
                                        String flag = String.valueOf(rs.getRow());
                                        writer.write(flag);
                                        writer.newLine();
                                        writer.flush();
                                        idHouse = rs.getString("idHouse");
                                        writer.write(idHouse);
                                        writer.newLine();
                                        writer.flush();
                                        country = rs.getString("country");
                                        writer.write(country);
                                        writer.newLine();
                                        writer.flush();
                                        type = rs.getString("type");
                                        writer.write(type);
                                        writer.newLine();
                                        writer.flush();
                                        countRoom = rs.getString("countRoom");
                                        writer.write(countRoom);
                                        writer.newLine();
                                        writer.flush();
                                        festive = rs.getString("festive");
                                        writer.write(festive);
                                        writer.newLine();
                                        writer.flush();
                                        priceDay = rs.getString("priceDay");
                                        writer.write(priceDay);
                                        writer.newLine();
                                        writer.flush();
                                    }
                                }
                                String flag = String.valueOf(0);
                                writer.write(flag);
                                writer.newLine();
                                writer.flush();
                                System.out.println("Произошел вывод всех недвижимостей");
                            }
                            case ("addHouse") -> {
                                String request1 = reader.readLine();
                                String request2 = reader.readLine();
                                String request3 = reader.readLine();
                                String request4 = reader.readLine();
                                String request5 = reader.readLine();

                                String ss = "INSERT INTO " + "house" + "("
                                        + "country" + "," + "type" + "," + "countRoom" +"," + "festive" +"," + "priceDay" +")" +
                                        "VALUES(?,?,?,?,?)";
                                try {
                                    PreparedStatement prst = DriverManager.getConnection(url, username, pasword).prepareStatement(ss);
                                    prst.setString(1, request1);
                                    prst.setString(2, request2);
                                    prst.setString(3, request3);
                                    prst.setString(4, request4);
                                    prst.setString(5, request5);

                                    prst.executeUpdate(); // заакинуть в бд
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                System.out.println("Произошла добавление недвижимости");
                            }
                            case ("deleteHouse") -> {
                                int ID = Integer.parseInt(reader.readLine());
                                try (Connection connection3 = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection3.createStatement();
                                    statement.executeUpdate("DELETE FROM house WHERE idHouse = " + ID);
                                } catch (SQLException e) {
                                    writer.write(e.getMessage());
                                    writer.newLine();
                                    writer.flush();
                                }
                                System.out.println("Удаления недвижимости с ID = " + ID);
                            }
                            case ("HousePoisk") -> {
                                try (Connection connection = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection.createStatement();
                                    String posik = reader.readLine();
                                    String sq_str = "SELECT * FROM house WHERE (festive = "+"'" + posik+"'" + ")";
                                    ResultSet rs = statement.executeQuery(sq_str);
                                    while (rs.next()) {
                                        String flag = String.valueOf(rs.getRow());
                                        writer.write(flag);
                                        writer.newLine();
                                        writer.flush();
                                        idHouse = rs.getString("idHouse");
                                        writer.write(idHouse);
                                        writer.newLine();
                                        writer.flush();
                                        country = rs.getString("country");
                                        writer.write(country);
                                        writer.newLine();
                                        writer.flush();
                                        type = rs.getString("type");
                                        writer.write(type);
                                        writer.newLine();
                                        writer.flush();
                                        countRoom = rs.getString("countRoom");
                                        writer.write(countRoom);
                                        writer.newLine();
                                        writer.flush();
                                        festive = rs.getString("festive");
                                        writer.write(festive);
                                        writer.newLine();
                                        writer.flush();
                                        priceDay = rs.getString("priceDay");
                                        writer.write(priceDay);
                                        writer.newLine();
                                        writer.flush();
                                    }
                                }
                                String flag = String.valueOf(0);
                                writer.write(flag);
                                writer.newLine();
                                writer.flush();
                                System.out.println("Произошел поиск праздничной недвижимости");
                            }
                            case ("editCountryHouse") -> {
                                String requestFabric = reader.readLine();
                                int cellFabric = Integer.parseInt(reader.readLine());
                                try (Connection connection2 = DriverManager.getConnection(url, username, pasword)) {
                                    statement = connection2.createStatement();
                                    try {
                                        statement.executeUpdate("UPDATE house SET country = '" + requestFabric + "' WHERE (idHouse = " + cellFabric + ")");
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                } catch (SQLException throwables) {
                                    writer.write(throwables.getMessage());
                                    writer.newLine();
                                    writer.flush();
                                }
                                System.out.println("Произошло редактирование города недвижимости с ID= " + cellFabric);
                            }
                            case ("editRoomHouse") -> {
                                String requestFabric = reader.readLine();
                                int cellFabric = Integer.parseInt(reader.readLine());
                                try (Connection connection2 = DriverManager.getConnection(url, username, pasword)) {
                                    statement = connection2.createStatement();
                                    try {
                                        statement.executeUpdate("UPDATE house SET countRoom = '" + requestFabric + "' WHERE (idHouse = " + cellFabric + ")");
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                } catch (SQLException throwables) {
                                    writer.write(throwables.getMessage());
                                    writer.newLine();
                                    writer.flush();
                                }
                                System.out.println("Произошло редактирование колл комнат недвижимости с ID= " + cellFabric);
                            }
                            case ("editPriceHouse") -> {
                                String requestFabric = reader.readLine();
                                int cellFabric = Integer.parseInt(reader.readLine());
                                try (Connection connection2 = DriverManager.getConnection(url, username, pasword)) {
                                    statement = connection2.createStatement();
                                    try {
                                        statement.executeUpdate("UPDATE house SET priceDay = '" + requestFabric + "' WHERE (idHouse = " + cellFabric + ")");
                                    } catch (SQLException e) {
                                        e.printStackTrace();
                                    }
                                } catch (SQLException throwables) {
                                    writer.write(throwables.getMessage());
                                    writer.newLine();
                                    writer.flush();
                                }
                                System.out.println("Произошло редактирование цены недвижимости с ID= " + cellFabric);
                            }
                            case ("houseVibor") -> {
                                try (Connection connection = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection.createStatement();
                                    String requestFabric = reader.readLine();
                                    String requestFabric2 = reader.readLine();
                                    System.out.println("Poisk:" + requestFabric);
                                    String sq_str = "SELECT * FROM house WHERE (country = '" + requestFabric + "' AND festive = '"+ requestFabric2 +"')";
                                    ResultSet rs = statement.executeQuery(sq_str);
                                    while (rs.next()) {
                                        String flag = String.valueOf(rs.getRow());
                                        writer.write(flag);
                                        writer.newLine();
                                        writer.flush();
                                        idHouse = rs.getString("idHouse");
                                        writer.write(idHouse);
                                        writer.newLine();
                                        writer.flush();
                                        country = rs.getString("country");
                                        writer.write(country);
                                        writer.newLine();
                                        writer.flush();
                                        type = rs.getString("type");
                                        writer.write(type);
                                        writer.newLine();
                                        writer.flush();
                                        countRoom = rs.getString("countRoom");
                                        writer.write(countRoom);
                                        writer.newLine();
                                        writer.flush();
                                        festive = rs.getString("festive");
                                        writer.write(festive);
                                        writer.newLine();
                                        writer.flush();
                                        priceDay = rs.getString("priceDay");
                                        writer.write(priceDay);
                                        writer.newLine();
                                        writer.flush();
                                    }
                                }
                                String flag = String.valueOf(0);
                                writer.write(flag);
                                writer.newLine();
                                writer.flush();
                                System.out.println("Произошел выбор нежвижимости");
                            }
                            case ("carVibor") -> {
                                try (Connection connection = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection.createStatement();
                                    String requestFabric = reader.readLine();
                                    String requestFabric2 = reader.readLine();
                                    System.out.println("Poisk:" + requestFabric);
                                    String sq_str = "SELECT * FROM car WHERE (brand = '" + requestFabric + "' AND priceDay >= "+ requestFabric2 +")";
                                    ResultSet rs = statement.executeQuery(sq_str);
                                    while (rs.next()) {
                                        String flag = String.valueOf(rs.getRow());
                                        writer.write(flag);
                                        writer.newLine();
                                        writer.flush();
                                        idCar = rs.getString("idCar");
                                        writer.write(idCar);
                                        writer.newLine();
                                        writer.flush();
                                        brand = rs.getString("brand");
                                        writer.write(brand);
                                        writer.newLine();
                                        writer.flush();
                                        year = rs.getString("year");
                                        writer.write(year);
                                        writer.newLine();
                                        writer.flush();
                                        volume = rs.getString("volume");
                                        writer.write(volume);
                                        writer.newLine();
                                        writer.flush();
                                        engine = rs.getString("engine");
                                        writer.write(engine);
                                        writer.newLine();
                                        writer.flush();
                                        priceDay = rs.getString("priceDay");
                                        writer.write(priceDay);
                                        writer.newLine();
                                        writer.flush();
                                    }
                                }
                                String flag = String.valueOf(0);
                                writer.write(flag);
                                writer.newLine();
                                writer.flush();
                                System.out.println("Произошел выбор транспорта");
                            }
                            case ("rentHouse") -> {
                                String req = reader.readLine();
                                String req1 = reader.readLine();
                                String req2 = reader.readLine();
                                String req3 = reader.readLine();
                                String req4 = reader.readLine();
                                String req5 = reader.readLine();

                                String ss = "INSERT INTO " + "renthouse" + "(" + "country" + "," + "priceDay" + "," + "time" +"," + "timeEnd" +"," + "userName" +")" + "VALUES(?,?,?,?,?)";
                                try {
                                    PreparedStatement prst = DriverManager.getConnection(url, username, pasword).prepareStatement(ss);
                                    prst.setString(1, req1);
                                    prst.setString(2, req2);
                                    prst.setString(3, req3);
                                    prst.setString(4, req4);
                                    prst.setString(5, req5);
                                    prst.executeUpdate(); // заакинуть в бд
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                try (Connection connection3 = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection3.createStatement();
                                    statement.executeUpdate("DELETE FROM house WHERE idHouse = " + req);
                                } catch (SQLException e) {
                                    writer.write(e.getMessage());
                                    writer.newLine();
                                    writer.flush();
                                }
                                System.out.println("Произошла бронирование недвижимости");
                            }
                            case ("rentCar") -> {
                                String req = reader.readLine();
                                String req1 = reader.readLine();
                                String req2 = reader.readLine();
                                String req3 = reader.readLine();
                                String req4 = reader.readLine();
                                String req5 = reader.readLine();
                                String req6 = reader.readLine();
                                String ss = "INSERT INTO " + "rentcar" + "(" + "brand" + "," + "year" + "," + "priceDay" +"," + "time" +"," + "timeEnd" +"," + "userName" +")" + "VALUES(?,?,?,?,?,?)";
                                try {
                                    PreparedStatement prst = DriverManager.getConnection(url, username, pasword).prepareStatement(ss);
                                    prst.setString(1, req1);
                                    prst.setString(2, req2);
                                    prst.setString(3, req3);
                                    prst.setString(4, req4);
                                    prst.setString(5, req5);
                                    prst.setString(6, req6);
                                    prst.executeUpdate(); // заакинуть в бд
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                try (Connection connection3 = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection3.createStatement();
                                    statement.executeUpdate("DELETE FROM car WHERE idCar = " + req);
                                } catch (SQLException e) {
                                    writer.write(e.getMessage());
                                    writer.newLine();
                                    writer.flush();
                                }
                                System.out.println("Произошла бронирование траанспорта");
                            }
                            case ("RentHouseInfo") -> {
                                try (Connection connection = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection.createStatement();
                                    String sq_str = "SELECT * FROM renthouse";
                                    ResultSet rs = statement.executeQuery(sq_str);
                                    while (rs.next()) {
                                        String flag = String.valueOf(rs.getRow());
                                        writer.write(flag);
                                        writer.newLine();
                                        writer.flush();
                                        idHouse = rs.getString("idRentHouse");
                                        writer.write(idHouse);
                                        writer.newLine();
                                        writer.flush();
                                        country = rs.getString("country");
                                        writer.write(country);
                                        writer.newLine();
                                        writer.flush();
                                        type = rs.getString("priceDay");
                                        writer.write(type);
                                        writer.newLine();
                                        writer.flush();
                                        countRoom = rs.getString("time");
                                        writer.write(countRoom);
                                        writer.newLine();
                                        writer.flush();
                                        festive = rs.getString("timeEnd");
                                        writer.write(festive);
                                        writer.newLine();
                                        writer.flush();
                                        priceDay = rs.getString("userName");
                                        writer.write(priceDay);
                                        writer.newLine();
                                        writer.flush();
                                    }
                                }
                                String flag = String.valueOf(0);
                                writer.write(flag);
                                writer.newLine();
                                writer.flush();
                                System.out.println("Произошел вывод арендованых домов");
                            }
                            case ("deleteHouseRent") -> {
                                String req = reader.readLine();
                                String req1 = reader.readLine();
                                String req2 = reader.readLine();
                                String req3 = reader.readLine();
                                String req4 = reader.readLine();
                                String req5 = reader.readLine();
                                String ss = "INSERT INTO " + "income" + "(" + "nameRent" + "," + "price" + "," + "prosrosh" +"," + "datee"+"," + "username"+")" + "VALUES(?,?,?,?,?)";
                                try {
                                    PreparedStatement prst = DriverManager.getConnection(url, username, pasword).prepareStatement(ss);
                                    prst.setString(1, req1);
                                    prst.setString(2, req2);
                                    prst.setString(3, req3);
                                    prst.setString(4, req4);
                                    prst.setString(5, req5);
                                    prst.executeUpdate(); // заакинуть в бд
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                try (Connection connection3 = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection3.createStatement();
                                    statement.executeUpdate("DELETE FROM renthouse WHERE idRentHouse = " + req);
                                } catch (SQLException e) {
                                    writer.write(e.getMessage());
                                    writer.newLine();
                                    writer.flush();
                                }
                                System.out.println("Произошла оплата арендованной недвижимости и возврат");
                            }
                            case ("RentCarInfo") -> {
                                try (Connection connection = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection.createStatement();
                                    String sq_str = "SELECT * FROM rentcar";
                                    ResultSet rs = statement.executeQuery(sq_str);
                                    while (rs.next()) {
                                        String flag = String.valueOf(rs.getRow());
                                        writer.write(flag);
                                        writer.newLine();
                                        writer.flush();
                                        idHouse = rs.getString("idRentCar");
                                        writer.write(idHouse);
                                        writer.newLine();
                                        writer.flush();
                                        country = rs.getString("brand");
                                        writer.write(country);
                                        writer.newLine();
                                        writer.flush();
                                        type = rs.getString("year");
                                        writer.write(type);
                                        writer.newLine();
                                        writer.flush();
                                        countRoom = rs.getString("priceDay");
                                        writer.write(countRoom);
                                        writer.newLine();
                                        writer.flush();
                                        festive = rs.getString("time");
                                        writer.write(festive);
                                        writer.newLine();
                                        writer.flush();
                                        priceDay = rs.getString("timeEnd");
                                        writer.write(priceDay);
                                        writer.newLine();
                                        writer.flush();
                                        userName = rs.getString("userName");
                                        writer.write(userName);
                                        writer.newLine();
                                        writer.flush();
                                    }
                                }
                                String flag = String.valueOf(0);
                                writer.write(flag);
                                writer.newLine();
                                writer.flush();
                                System.out.println("Произошел вывод арендованых трансопртов");
                            }
                            case ("deleteCarRent") -> {
                                String req = reader.readLine();
                                String req1 = reader.readLine();
                                String req2 = reader.readLine();
                                String req3 = reader.readLine();
                                String req4 = reader.readLine();
                                String req5 = reader.readLine();
                                String ss = "INSERT INTO " + "income" + "(" + "nameRent" + "," + "price" + "," + "prosrosh" +"," + "datee"+"," + "username"+")" + "VALUES(?,?,?,?,?)";
                                try {
                                    PreparedStatement prst = DriverManager.getConnection(url, username, pasword).prepareStatement(ss);
                                    prst.setString(1, req1);
                                    prst.setString(2, req2);
                                    prst.setString(3, req3);
                                    prst.setString(4, req4);
                                    prst.setString(5, req5);
                                    prst.executeUpdate(); // заакинуть в бд
                                } catch (SQLException e) {
                                    e.printStackTrace();
                                }
                                try (Connection connection3 = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection3.createStatement();
                                    statement.executeUpdate("DELETE FROM rentcar WHERE idRentCar = " + req);
                                } catch (SQLException e) {
                                    writer.write(e.getMessage());
                                    writer.newLine();
                                    writer.flush();
                                }
                                System.out.println("Произошла оплата арендованного транспорта и возврат");
                            }
                            case ("Data1gra") -> {
                                try (Connection connection = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection.createStatement();
                                    String req = reader.readLine();
                                    String sq_str = "SELECT * FROM income WHERE (nameRent = '" + req + "'"+")";
                                    ResultSet rs = statement.executeQuery(sq_str);
                                    while (rs.next()) {
                                        String flag = String.valueOf(rs.getRow());
                                        writer.write(flag);
                                        writer.newLine();
                                        writer.flush();
                                        price = rs.getString("price");
                                        writer.write(price);
                                        writer.newLine();
                                        writer.flush();
                                        datee = rs.getString("datee");
                                        writer.write(datee);
                                        writer.newLine();
                                        writer.flush();
                                    }
                                }
                                String flag = String.valueOf(0);
                                writer.write(flag);
                                writer.newLine();
                                writer.flush();
                                System.out.println("Произошел построение графика по недвижимости");
                            }
                            case ("Data2gra") -> {
                                try (Connection connection = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection.createStatement();
                                    String sq_str = "SELECT * FROM income WHERE (nameRent = '" + "Транспорт" + "'"+")";
                                    ResultSet rs = statement.executeQuery(sq_str);
                                    while (rs.next()) {
                                        String flag = String.valueOf(rs.getRow());
                                        writer.write(flag);
                                        writer.newLine();
                                        writer.flush();
                                        price = rs.getString("price");
                                        writer.write(price);
                                        writer.newLine();
                                        writer.flush();
                                        datee = rs.getString("datee");
                                        writer.write(datee);
                                        writer.newLine();
                                        writer.flush();
                                    }
                                }
                                String flag = String.valueOf(0);
                                writer.write(flag);
                                writer.newLine();
                                writer.flush();
                                System.out.println("Произошел построение графика по транспорту");
                            }
                            case ("History") -> {
                                try (Connection connection = DriverManager.getConnection(url, username, pasword)) {
                                    Statement statement = connection.createStatement();
                                    String req = reader.readLine();
                                    String sq_str = "SELECT * FROM income WHERE (username = '" + req + "'"+")";
                                    ResultSet rs = statement.executeQuery(sq_str);
                                    while (rs.next()) {
                                        String flag = String.valueOf(rs.getRow());
                                        writer.write(flag);
                                        writer.newLine();
                                        writer.flush();
                                        idIncome = rs.getString("idIncome");
                                        writer.write(idIncome);
                                        writer.newLine();
                                        writer.flush();
                                        nameRent = rs.getString("nameRent");
                                        writer.write(nameRent);
                                        writer.newLine();
                                        writer.flush();
                                        price = rs.getString("price");
                                        writer.write(price);
                                        writer.newLine();
                                        writer.flush();
                                        prosrosh = rs.getString("prosrosh");
                                        writer.write(prosrosh);
                                        writer.newLine();
                                        writer.flush();
                                        datee = rs.getString("datee");
                                        writer.write(datee);
                                        writer.newLine();
                                        writer.flush();
                                        datee = rs.getString("username");
                                        writer.write(datee);
                                        writer.newLine();
                                        writer.flush();
                                    }
                                    System.out.println("Произошел вывод истории аренды пользователя: " + req);
                                }
                                String flag = String.valueOf(0);
                                writer.write(flag);
                                writer.newLine();
                                writer.flush();

                            }
                        }
                    }
                }
            }

    } catch(SocketException e){

    } catch(IOException |SQLException| ClassNotFoundException e){
        e.printStackTrace();
    }
    }
}