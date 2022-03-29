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
import sample.Model.HOADON;
import sample.Model.TONGDOANHTHU;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerTONGDOANHTHU implements Initializable {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<TONGDOANHTHU> data;


    @FXML
    private Button btnthoat;

    @FXML
    private DatePicker txttn;

    @FXML
    private DatePicker txtdn;

    @FXML
    private TableView<TONGDOANHTHU> tableTDT;

    @FXML
    private TableColumn<?, ?> coltongdoanhthu;

    @FXML
    private Button btnLOAD;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = sample.DBConnection.pmartConnection();
        data = FXCollections.observableArrayList();
        setCellTable();
//        LoadDataTableView();
//        setcellvalueformtableview();
//        SearchNV();
    }
    private void setCellTable() {
        coltongdoanhthu.setCellValueFactory(new PropertyValueFactory<>("DOANHTHU"));
    }

    private void cleardata() {
        for ( int i = 0; i<tableTDT.getItems().size(); i++) {
            tableTDT.getItems().clear();
        }
    }
    @FXML
    public void LoadDataTableView(javafx.event.ActionEvent actionEvent) {
        if(txttn.getValue() != null && txtdn.getValue() != null){
        cleardata();
        try {
            pst = con.prepareStatement("select sum(TONGHD) as DOANHTHU from HOADON WHERE NGAYLAP BETWEEN '"+txttn.getValue()+"' and '"+txtdn.getValue()+"'");
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new TONGDOANHTHU(rs.getString(1)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControllerTONGDOANHTHU.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableTDT.setItems(data);
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
