package sample;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Controllerr.ControllerCTHD;
import sample.Controllerr.ControllerNHANVIEN;
import sample.Model.HOADON;

import javafx.scene.control.DatePicker;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javafx.collections.FXCollections.observableArrayList;

public class ControllerHOADON implements Initializable {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<HOADON> data;

    @FXML
    private TextField txtmahd;


    @FXML
    private TextField txtmakh;

    @FXML
    private TextField txtngaylap;

    @FXML
    private TextField txttonghd;

    @FXML
    private Button btnthem;

    @FXML
    private Button btnxoa;

    @FXML
    private Button btnLOAD;

    @FXML
    private Button btnsua;

    @FXML
    private Button btnthoat;

    @FXML
    private TableView<HOADON> tablehoadon;

    @FXML
    private TableColumn<?, ?> colummahd;

    @FXML
    private TableColumn<?, ?> colummanv;

    @FXML
    private TableColumn<?, ?> colummakh;

    @FXML
    private TableColumn<?, ?> columngaylap;

    @FXML
    private TableColumn<?, ?> columtonghd;

    @FXML
    private TextField txtsearch;

    @FXML
    private DatePicker dpngaylap;

    @FXML
    private ComboBox<String> cbomanv;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = sample.DBConnection.pmartConnection();
        data = FXCollections.observableArrayList();
        loadcbo();
//        LocalDate s = LocalDate.now();
//        String a = String.valueOf(s+" "+ String.valueOf(LocalTime.now()));
//        dpngaylap.setValue(LocalDate.parse(a));

        setCellTable();
        LoadDataTableView();
        setcellvalueformtableview();
        SearchNV();
    }
    private void setCellTable() {

        colummahd.setCellValueFactory(new PropertyValueFactory<>("MAHD"));
        colummanv.setCellValueFactory(new PropertyValueFactory<>("MANV"));
        colummakh.setCellValueFactory(new PropertyValueFactory<>("MAKH"));
        columngaylap.setCellValueFactory(new PropertyValueFactory<>("NGAYLAP"));
        columtonghd.setCellValueFactory(new PropertyValueFactory<>("TONGHD"));

    }
    private void cleardata() {
        txtmahd.clear();
        txtmakh.clear();



    }
    private void LoadDataTableView() {
        data.clear();
        try {
            pst = con.prepareStatement("SELECT MAHD,MANV,MAKH,NGAYLAP,TONGHD FROM HOADON");
            rs = pst.executeQuery();
            while (rs.next()) {

                data.add(new HOADON(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));

            }

        } catch (SQLException ex) {
            Logger.getLogger(ControllerHOADON.class.getName()).log(Level.SEVERE, null, ex);
        }
        tablehoadon.setItems(data);

    }
    private void setcellvalueformtableview()    {
        tablehoadon.setOnMouseClicked(e->{
            HOADON p1 = tablehoadon.getItems().get(tablehoadon.getSelectionModel().getFocusedIndex());
            txtmahd.setText(p1.getMAHD());
            cbomanv.setValue(p1.getMANV());
            txtmakh.setText(p1.getMAKH());
            //txtngaylap.setText(p1.getNGAYLAP());
            //dpngaylap.setValue(p1.getNGAYLAP());
            //txttonghd.setText(p1.getTONGHD());
    });
    }

    private void SearchNV()
    {
        txtsearch.setOnKeyReleased(e->{
            if(txtsearch.getText().equals(""))
            {
                LoadDataTableView();
            }
            else {
                data.clear();
                String sql ="Select *from HOADON Where MAHD LIKE '%"+txtsearch.getText()+"%'"
                        + "UNION Select *from HOADON Where MANV LIKE '%"+txtsearch.getText()+"%' "
                        + "UNION Select *from HOADON Where MAKH LIKE '%"+txtsearch.getText()+"%'"
                        + "UNION Select *from HOADON Where NGAYLAP LIKE '%"+txtsearch.getText()+"%'"
                        + "UNION Select *from HOADON Where TONGHD LIKE '%"+txtsearch.getText()+"%'";
                try {
                    pst =con.prepareStatement(sql);

                    rs=pst.executeQuery();
                    while (rs.next())
                    {
                        //System.out.println(""+rs.getString(1));
                        data.add(new HOADON(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5)));
                    }
                    tablehoadon.setItems(data);
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerNHANVIEN.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }

    @FXML
    private void handleAddHoaDon(ActionEvent event) throws SQLException {
        if(txtmahd.getText().isEmpty()== false && cbomanv.getValue().isEmpty() == false&& txtmakh.getText().isEmpty() == false&& dpngaylap.getValue() != null){
        String sql = "Insert Into HOADON(MAHD,MANV,MAKH,NGAYLAP,TONGHD) Values(?,?,?,?,?)";
        String mahd = txtmahd.getText();
        String manv = cbomanv.getValue();
        String makh = txtmakh.getText();
       // String ngaylap = txtngaylap.getText();
        LocalDate ngaylap = dpngaylap.getValue();
        String tonghd = txttonghd.getText();

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, mahd);
            pst.setString(2, manv);
            pst.setString(3, makh);
            pst.setString(4, String.valueOf(ngaylap));
            pst.setString(5, tonghd);

            int i = pst.executeUpdate();
            if (i == 1) {
                System.out.println("Data Insert Successfully");
                LoadDataTableView();
                Alert VV =new Alert(Alert.AlertType.WARNING,"Thêm Thành Công",ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
                cleardata();
                try
                {
                    Parent root = FXMLLoader.load(getClass().getResource("CTHD.fxml"));
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
            Alert VV =new Alert(Alert.AlertType.WARNING," Lỗi Nhập Liệu",ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
            cleardata();
            Logger.getLogger(ControllerHOADON.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            pst.close();
        }}else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Cần điền đầy đủ thông tin!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }
    }

    public void handleUpdateHoaDon(ActionEvent event) {
        if(txtmahd.getText().isEmpty()== false && cbomanv.getValue().isEmpty() == false&& txtmakh.getText().isEmpty() == false&& dpngaylap.getValue() != null){
        String sql = "Update HOADON  set  MANV = ?, NGAYLAP = ? WHERE MAHD = ? ";
        try {
            String mahd = txtmahd.getText();
            String manv = cbomanv.getValue();
            LocalDate ngaylap = dpngaylap.getValue();

            pst = con.prepareStatement(sql);
            pst.setString(1, manv);
            pst.setString(2, String.valueOf(ngaylap));
            pst.setString(3, mahd);


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
        }}else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Cần điền đầy đủ thông tin!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }
    }
    public void handleDeleteHoaDon(ActionEvent event) throws SQLException {
        String sql = "delete from HOADON where MAHD = ? ";

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,txtmahd.getText());

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
            Logger.getLogger(ControllerNHANVIEN.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @FXML
    void handlecancel(ActionEvent event) {
        Stage stage = (Stage) btnthoat.getScene().getWindow();
        stage.close();
    }

    @FXML
    void LOADOnAction(ActionEvent event) {
        LoadDataTableView();
    }

    private void loadcbo(){
        try{
            List<String> options = new ArrayList<>();
            pst = con.prepareStatement("SELECT * FROM NHANVIEN");
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
