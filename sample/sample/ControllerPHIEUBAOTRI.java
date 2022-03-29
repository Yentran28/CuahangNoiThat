package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Model.PHIEUBAOTRI;

import javafx.scene.control.DatePicker;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.scene.control.ComboBox;

import static javafx.collections.FXCollections.observableArrayList;

public class ControllerPHIEUBAOTRI implements Initializable {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<PHIEUBAOTRI> data;

    @FXML
    private TextField txtmabt;

    @FXML
    private TextField txtmahd;

    @FXML
    private ComboBox<String> cbomanv;

    @FXML
    private TextField txttpbt;

    @FXML
    private TextField txtngaybt;

    @FXML
    private TableView<PHIEUBAOTRI> tablepbt;

    @FXML
    private TableColumn<?, ?> colmabt;

    @FXML
    private TableColumn<?, ?> colmahd;

    @FXML
    private TableColumn<?, ?> colmanv;

    @FXML
    private TableColumn<?, ?> coltpbt;

    @FXML
    private TableColumn<?, ?> colngaybt;

//    @FXML
//    private DatePicker DPngaybt;
    @FXML
    private Button btnxoa;

    @FXML
    private Button btnLOAD;

    @FXML
    private Button btnthoat;

    @FXML
    private DatePicker dbngaybt;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = sample.DBConnection.pmartConnection();
        data = FXCollections.observableArrayList();
        loadcbo();
        setCellTable();
        LoadDataTableView();
        setcellvalueformtableview();
        //SearchNV();
    }
    private void setCellTable() {
        colmabt.setCellValueFactory(new PropertyValueFactory<>("MABT"));
        colmahd.setCellValueFactory(new PropertyValueFactory<>("MAHD"));
        colmanv.setCellValueFactory(new PropertyValueFactory<>("MANV"));
        coltpbt.setCellValueFactory(new PropertyValueFactory<>("TONGPHIBT"));
        colngaybt.setCellValueFactory(new PropertyValueFactory<>("NGAYBT"));
    }
    private void cleardata() {
        txtmabt.clear();
        txtmahd.clear();
        txttpbt.clear();
    }
    private void LoadDataTableView() {
        data.clear();
        try {
            pst = con.prepareStatement("SELECT MABT,MAHD,MANV,TONGPHIBT,NGAYBT FROM PHIEUBAOTRI");
            rs = pst.executeQuery();
            while (rs.next()) {

                data.add(new PHIEUBAOTRI(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));

            }

        } catch (SQLException ex) {
            Logger.getLogger(ControllerPHIEUBAOTRI.class.getName()).log(Level.SEVERE, null, ex);
        }
        tablepbt.setItems(data);
    }

    private void setcellvalueformtableview(){
        tablepbt.setOnMouseClicked(e->{
            PHIEUBAOTRI p1 = tablepbt.getItems().get(tablepbt.getSelectionModel().getFocusedIndex());
            txtmabt.setText(p1.getMABT());
            txtmahd.setText(p1.getMAHD());
            cbomanv.setValue(p1.getMANV());
            //txttpbt.setText(p1.getTONGPHIBT());
            //DPngaybt.setPromptText(p1.getNGAYBT());
        });
    }
    @FXML
    private void handleAdd(ActionEvent event) throws SQLException {
        if(txtmabt.getText().isEmpty()== false && txtmahd.getText().isEmpty() == false&& cbomanv.getValue().isEmpty() == false&& dbngaybt.getValue() != null){
        String sql = "Insert Into PHIEUBAOTRI(MABT,MAHD,MANV,TONGPHIBT,NGAYBT) Values(?,?,?,?,?)";
        String bt = txtmabt.getText();
        String hd = txtmahd.getText();
        String nv = cbomanv.getValue();
        String phi = txttpbt.getText();
        LocalDate ngaylap = dbngaybt.getValue();

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, bt);
            pst.setString(2, hd);
            pst.setString(3, nv);
            pst.setString(4, phi);
            pst.setString(5, String.valueOf(ngaylap));

            int i = pst.executeUpdate();
            if (i == 1) {
                System.out.println("Data Insert Successfully");
                LoadDataTableView();
                Alert VV = new Alert(Alert.AlertType.WARNING, "Thêm Thành Công", ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
                cleardata();
                try
                {
                    Parent root = FXMLLoader.load(getClass().getResource("CTPBT.fxml"));
                    Stage NV =new Stage();
                    NV.initStyle(StageStyle.UNDECORATED);
                    NV.setScene(new Scene(root, 800, 700));
                    NV.showAndWait();
                }catch (Exception e)
                {
                    e.printStackTrace();
                    e.getCause();
                }
            }
        } catch (SQLException ex) {
            Alert VV = new Alert(Alert.AlertType.WARNING, " Lỗi Nhập Liệu", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
            cleardata();
            Logger.getLogger(ControllerPHIEUBAOTRI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            pst.close();
        }
        }else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Cần điền đầy đủ thông tin!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }
    }
    public void handleDelete(ActionEvent event) throws SQLException {
        String sql = "delete from PHIEUBAOTRI where MABT = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,txtmabt.getText());
            int i=  pst.executeUpdate();
            if(i==1){
                System.out.println("Data Delete Successfully");
                LoadDataTableView();
                Alert VV =new Alert(Alert.AlertType.WARNING,"Xóa Thành Công",ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
                cleardata();

            }
        }catch(SQLException ex){
            Alert VV =new Alert(Alert.AlertType.WARNING," Lỗi Nhập Liệu",ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
            cleardata();
            Logger.getLogger(ControllerPHIEUBAOTRI.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void CancelButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnthoat.getScene().getWindow();
        stage.close();
    }
    public void LOADOnAction(ActionEvent actionEvent) {
        LoadDataTableView();
    }

    public void handleUpdateHoaDon(ActionEvent event) {
        if(txtmabt.getText().isEmpty()== false && txtmahd.getText().isEmpty() == false&& cbomanv.getValue().isEmpty() == false&& dbngaybt.getValue() != null){
        String sql = "Update PHIEUBAOTRI  set  MAHD= ?, MANV = ?, NGAYBT=? WHERE MABT = ? ";
        try {
            String mabt = txtmabt.getText();
            String mahd = txtmahd.getText();
            String manv = cbomanv.getValue();
            LocalDate ngaylap = dbngaybt.getValue();

            pst = con.prepareStatement(sql);
            pst.setString(1, mahd);
            pst.setString(2, manv);
            pst.setString(3, String.valueOf(ngaylap));
            pst.setString(4, mabt);


            int i = pst.executeUpdate();
            if (i == 1) {
                System.out.println("Data Insert Successfully");
                LoadDataTableView();
                Alert VV =new Alert(Alert.AlertType.WARNING,"Sửa Thành Công",ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
                cleardata();

            }
        } catch (SQLException ex) {
            Alert VV =new Alert(Alert.AlertType.WARNING," Lỗi Nhập Liệu",ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
            cleardata();
            Logger.getLogger(ControllerHOADON.class.getName()).log(Level.SEVERE, null, ex);
        }
    }else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Cần điền đầy đủ thông tin!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }
    }
    private void loadcbo(){
        try{
            List<String> options = new ArrayList<>();
            pst = con.prepareStatement("select MANV from NHANVIEN");
            rs = pst.executeQuery();
            while(rs.next()){
                String name = rs.getString("MANV");
                options.add(name);
            }
            cbomanv.setItems(observableArrayList(options));
        }catch(SQLException ex){
            Logger.getLogger(ControllerHOADON.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
}
