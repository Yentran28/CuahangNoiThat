package sample.Controllerr;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import sample.Model.KHACHHANG;

import javafx.fxml.FXML;
import sample.Model.KHACHHANG;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerKHACHHANG implements Initializable {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<KHACHHANG> data;

    @FXML
    private TextField txtma;

    @FXML
    private TextField txtten;

    @FXML
    private TextField txtsdt;

    @FXML
    private Button btnthem;

    @FXML
    private Button btnxoa;

    @FXML
    private Button btnsua;

    @FXML
    private Button btnthoat;

    @FXML
    private TextField txtdc;

    @FXML
    private TableView<KHACHHANG> tablekh;

    @FXML
    private TableColumn<?, ?> colma;

    @FXML
    private TableColumn<?, ?> colten;

    @FXML
    private TableColumn<?, ?> colsdt;

    @FXML
    private TableColumn<?, ?> coldc;

    @FXML
    private TextField txtsearch;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        con = sample.DBConnection.pmartConnection();
        data = FXCollections.observableArrayList();
        setCellTable();
        LoadDataTableView();
        setcellvalueformtableview();
        SearchNV();
    }
    private void setCellTable() {
        colma.setCellValueFactory(new PropertyValueFactory<>("MAKH"));
        colten.setCellValueFactory(new PropertyValueFactory<>("TENKH"));
        colsdt.setCellValueFactory(new PropertyValueFactory<>("SĐT"));
        coldc.setCellValueFactory(new PropertyValueFactory<>("DIACHI"));

    }
    private void cleardata() {
        txtma.clear();
        txtten.clear();
        txtsdt.clear();
        txtdc.clear();

    }
    private void LoadDataTableView() {
        data.clear();
        try {
            pst = con.prepareStatement("SELECT * FROM KHACHHANG");
            rs = pst.executeQuery();
            while (rs.next()) {
                data.add(new KHACHHANG(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ControllerKHACHHANG.class.getName()).log(Level.SEVERE, null, ex);
        }
        tablekh.setItems(data);
    }

    private void setcellvalueformtableview()    {
        tablekh.setOnMouseClicked(e->{
            KHACHHANG p1 = tablekh.getItems().get(tablekh.getSelectionModel().getFocusedIndex());
            txtma.setText(p1.getMAKH());
            txtten.setText(p1.getTENKH());
            txtsdt.setText(p1.getSĐT());
            txtdc.setText(p1.getDIACHI());
        });
    }
    @FXML
    private void handleAdd(ActionEvent event) throws SQLException {
        if(txtma.getText().isEmpty()== false && txtten.getText().isEmpty() == false&& txtsdt.getText().isEmpty() == false&& txtdc.getText().isEmpty() == false){
            if(checkPhone(txtsdt.getText())==true&& txtsdt.getText().length()>8 && txtsdt.getText().length()<12){
        String sql = "Insert Into KHACHHANG(MAKH,TENKH,SĐT,DIACHI) Values(?,?,?,?)";
        String ma = txtma.getText();
        String ten = txtten.getText();
        String sdt = txtsdt.getText();
        String dc = txtdc.getText();
        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, ma);
            pst.setString(2, ten);
            pst.setString(3, sdt);
            pst.setString(4, dc);

            int i = pst.executeUpdate();
            if (i == 1) {
                System.out.println("Data Insert Successfully");
                LoadDataTableView();
                Alert VV = new Alert(Alert.AlertType.WARNING, "Thêm Thành Công", ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
                cleardata();
            }
        } catch (SQLException ex) {
            Alert VV = new Alert(Alert.AlertType.WARNING, " Lỗi Nhập Liệu", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
            cleardata();
            Logger.getLogger(ControllerKHACHHANG.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            pst.close();
        }}else{
                Alert VV = new Alert(Alert.AlertType.WARNING, " Số điện thoại không hợp lệ!", ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
            }
        }else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Cần điền đầy đủ thông tin!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }
    }

    public void handleDelete(ActionEvent event) throws SQLException {
        String sql = "delete from KHACHHANG where MAKH = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,txtma.getText());
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
            Logger.getLogger(ControllerKHACHHANG.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleUpdate(ActionEvent event) {
        if(txtma.getText().isEmpty()== false && txtten.getText().isEmpty() == false&& txtsdt.getText().isEmpty() == false&& txtdc.getText().isEmpty() == false){
            if(checkPhone(txtsdt.getText())==true&& txtsdt.getText().length()>8 && txtsdt.getText().length()<12){
        String sql = "Update KHACHHANG  set  TENKH=?,SĐT=?,DIACHI=? WHERE MAKH = ? ";
        try {
            String ma = txtma.getText();
            String ten = txtten.getText();
            String sdt = txtsdt.getText();
            String dc = txtdc.getText();
            pst = con.prepareStatement(sql);
            pst.setString(4, ma);
            pst.setString(1, ten);
            pst.setString(2, sdt);
            pst.setString(3, dc);
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
            Logger.getLogger(ControllerKHACHHANG.class.getName()).log(Level.SEVERE, null, ex);
        }}else{
                Alert VV = new Alert(Alert.AlertType.WARNING, " Số điện thoại không hợp lệ!", ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
            }
        }else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Cần điền đầy đủ thông tin!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }
    }
    public void CancelButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnthoat.getScene().getWindow();
        stage.close();
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
                String sql ="Select *from KHACHHANG Where MAKH LIKE '%"+txtsearch.getText()+"%'"
                        + "UNION Select *from KHACHHANG Where TENKH LIKE N'%"+txtsearch.getText()+"%' "
                        + "UNION Select *from KHACHHANG Where SĐT LIKE '%"+txtsearch.getText()+"%'"
                        + "UNION Select *from KHACHHANG Where DIACHI LIKE N'%"+txtsearch.getText()+"%'";
                try {
                    pst =con.prepareStatement(sql);

                    rs=pst.executeQuery();
                    while (rs.next())
                    {
                        //System.out.println(""+rs.getString(1));
                        data.add(new KHACHHANG(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
                    }
                    tablekh.setItems(data);
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerNHANVIEN.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }
    public static boolean checkPhone(String value) {

        try {
            Pattern pattern = Pattern.compile("^[0-9]*$");
            Matcher matcher = pattern.matcher(value);
            return matcher.matches();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
