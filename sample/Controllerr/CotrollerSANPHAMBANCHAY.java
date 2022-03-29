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
import sample.Model.DOANHTHUNHANVIEN;
import sample.Model.SANPHAMBANCHAY;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.collections.FXCollections.observableArrayList;

public class CotrollerSANPHAMBANCHAY implements Initializable {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<SANPHAMBANCHAY> data;

    @FXML
    private DatePicker txttn;

    @FXML
    private DatePicker txtdn;

    @FXML
    private TableView<SANPHAMBANCHAY> tablespbc;

    @FXML
    private TableColumn<?, ?> colmsp;

    @FXML
    private TableColumn<?, ?> colslb;

    @FXML
    private Button btnLOAD;

    @FXML
    private Button btnthoat;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = sample.DBConnection.pmartConnection();
        data = FXCollections.observableArrayList();
        setCellTable();
    }
    private void setCellTable() {
        colmsp.setCellValueFactory(new PropertyValueFactory<>("MASP"));
        colslb.setCellValueFactory(new PropertyValueFactory<>("SOLUONGBAN"));
    }

    private void cleardata() {
        for ( int i = 0; i<tablespbc.getItems().size(); i++) {
            tablespbc.getItems().clear();
        }
    }
    @FXML
    public void LoadDataTableView(javafx.event.ActionEvent actionEvent) {
        if(txttn.getValue() != null && txtdn.getValue() != null){
        cleardata();
        try {
            //pst = con.prepareStatement("SELECT * FROM HOADON");
            pst = con.prepareStatement("select MASP,count(SL) as SOLUONGBAN \n" +
                    "from HOADON a,CTHD b\n" +
                    "WHERE  a.MAHD=b.MAHD and NGAYLAP >= '"+txttn.getValue()+"' and NGAYLAP <= '"+txtdn.getValue()+"'\n" +
                    "group by MASP\n" +
                    "order by SOLUONGBAN DESC");
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new SANPHAMBANCHAY(rs.getString(1),rs.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CotrollerSANPHAMBANCHAY.class.getName()).log(Level.SEVERE, null, ex);
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
