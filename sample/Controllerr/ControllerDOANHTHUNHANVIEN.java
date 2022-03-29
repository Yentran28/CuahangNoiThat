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
import sample.Model.TONGDOANHTHU;

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

public class ControllerDOANHTHUNHANVIEN implements Initializable {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<DOANHTHUNHANVIEN> data;

    @FXML
    private DatePicker txttn;

    @FXML
    private DatePicker txtdn;

    @FXML
    private TableView<DOANHTHUNHANVIEN> tableDTNV;

    @FXML
    private TableColumn<?, ?> colmanv;

    @FXML
    private TableColumn<?, ?> coltongdoanhthu;

    @FXML
    private Button btnLOAD;

    @FXML
    private ComboBox<String> cbomnv;

    @FXML
    private Button btnthoat;

    @FXML
    private TextField txtmnv;
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = sample.DBConnection.pmartConnection();
        data = FXCollections.observableArrayList();
        setCellTable();
        loadCBB();
    }
    private void setCellTable() {
        colmanv.setCellValueFactory(new PropertyValueFactory<>("MANV"));
        coltongdoanhthu.setCellValueFactory(new PropertyValueFactory<>("DOANHTHU"));
    }

    private void cleardata() {
        for ( int i = 0; i<tableDTNV.getItems().size(); i++) {
            tableDTNV.getItems().clear();
        }
    }
    @FXML
    public void LoadDataTableView(javafx.event.ActionEvent actionEvent) {
        if(txttn.getValue() != null && txtdn.getValue() != null && cbomnv.getValue().isEmpty()==false){
        cleardata();
        try {
            //pst = con.prepareStatement("SELECT * FROM HOADON");
            pst = con.prepareStatement("select MANV, sum(TONGHD) as DOANHTHU\n" +
                    "from HOADON\n" +
                    "WHERE NGAYLAP >= '"+txttn.getValue()+"' and NGAYLAP <= '"+txtdn.getValue()+"' and MANV='"+cbomnv.getValue()+"'\n" +
                    "group by MANV");
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new DOANHTHUNHANVIEN(rs.getString(1),rs.getString(2)));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ControllerDOANHTHUNHANVIEN.class.getName()).log(Level.SEVERE, null, ex);
        }
        tableDTNV.setItems(data);
    }else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Cần điền đầy đủ thông tin!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }
    }
    private void loadCBB(){
        try{
            List<String> options = new ArrayList<>();
            pst = con.prepareStatement("SELECT * FROM NHANVIEN");
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("MANV");
                options.add(name);
            }

            cbomnv.setItems(observableArrayList(options));
        }catch(SQLException ex){
            Logger.getLogger(ControllerCTHD.class.getName()).log(Level.SEVERE,null,ex);
        }

    }
    @FXML
    void handlecancel(ActionEvent event) {
        Stage stage = (Stage) btnthoat.getScene().getWindow();
        stage.close();
    }
}
