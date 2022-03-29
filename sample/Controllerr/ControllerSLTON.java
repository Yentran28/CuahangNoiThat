package sample.Controllerr;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Model.SANPHAMBANCHAY;
import sample.Model.SLTON;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerSLTON implements Initializable {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<SLTON> data;
    @FXML
    private TableView<SLTON> tablespbc;

    @FXML
    private TableColumn<?, ?> colmasp;

    @FXML
    private TableColumn<?, ?> coltensp;

    @FXML
    private TableColumn<?, ?> colslt;

    @FXML
    private Button btnLOAD;

    @FXML
    private Button btnthoat;

    @FXML
    private TextField txtslt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = sample.DBConnection.pmartConnection();
        data = FXCollections.observableArrayList();
        setCellTable();
    }
    private void setCellTable() {
        colmasp.setCellValueFactory(new PropertyValueFactory<>("MASP"));
        coltensp.setCellValueFactory(new PropertyValueFactory<>("TENSP"));
        colslt.setCellValueFactory(new PropertyValueFactory<>("SLTON"));
    }
    private void cleardata() {
        for ( int i = 0; i<tablespbc.getItems().size(); i++) {
            tablespbc.getItems().clear();
        }
    }
    @FXML
    public void LoadDataTableView(javafx.event.ActionEvent actionEvent) {
        if(txtslt.getText().isEmpty()== false){
        cleardata();
        try {
            pst = con.prepareStatement("select MASP,TENSP,SLTON\n" +
                    "from SANPHAM\n" +
                    "WHERE SLTON<'"+txtslt.getText()+"'");
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new SLTON(rs.getString(1),rs.getString(2),rs.getString(3)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControllerSLTON.class.getName()).log(Level.SEVERE, null, ex);
        }
        tablespbc.setItems(data);
        }else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Cần điền đầy đủ thông tin!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }
    }
    @FXML
    void handlecancel(ActionEvent event) {
        Stage stage = (Stage) btnthoat.getScene().getWindow();
        stage.close();
    }
}
