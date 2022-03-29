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
import sample.Model.LOAI;
import sample.Model.SANPHAM;

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


public class ControllerSANPHAM implements Initializable {
    private Connection con = null;
    private PreparedStatement pst = null;
    private ResultSet rs = null;
    private ObservableList<SANPHAM> data;

    @FXML
    private TextField txtmasp;

    @FXML
    private TextField txtten;

    @FXML
    private TextField txtvl;

    @FXML
    private Button btnthem;

    @FXML
    private Button btnxoa;

    @FXML
    private Button btnsua;

    @FXML
    private Button btnthoat;

    @FXML
    private TextField txtthbh;

    @FXML
    private TextField txtsl;

    @FXML
    private TextField txtgia;

    @FXML
    private TextField txtmatl;

    @FXML
    private TextField txtmakho;

    @FXML
    private TableView<SANPHAM> tablesp;

    @FXML
    private TableColumn<?, ?> colmasp;

    @FXML
    private TableColumn<?, ?> colten;

    @FXML
    private TableColumn<?, ?> colvl;

    @FXML
    private TableColumn<?, ?> colthbh;

    @FXML
    private TableColumn<?, ?> colsl;

    @FXML
    private TableColumn<?, ?> colgia;

    @FXML
    private TableColumn<?, ?> colmatl;

    @FXML
    private TableColumn<?, ?> colmakho;

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
        colmasp.setCellValueFactory(new PropertyValueFactory<>("MASP"));
        colten.setCellValueFactory(new PropertyValueFactory<>("TENSP"));
        colvl.setCellValueFactory(new PropertyValueFactory<>("VATLIEU"));
        colthbh.setCellValueFactory(new PropertyValueFactory<>("THOIHANBAOHANH"));
        colsl.setCellValueFactory(new PropertyValueFactory<>("SLTON"));
        colgia.setCellValueFactory(new PropertyValueFactory<>("GIA"));
        colmatl.setCellValueFactory(new PropertyValueFactory<>("MATL"));
        colmakho.setCellValueFactory(new PropertyValueFactory<>("MAKHO"));
    }
    private void cleardata() {
        txtmasp.clear();
        txtten.clear();
        txtvl.clear();
        txtthbh.clear();
        txtsl.clear();
        txtgia.clear();
        txtmatl.clear();
        txtmakho.clear();
    }
    private void LoadDataTableView() {
        data.clear();
        try {
            pst = con.prepareStatement("SELECT * FROM SANPHAM");
            rs = pst.executeQuery();
            while (rs.next()) {

                data.add(new SANPHAM(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));

            }

        } catch (SQLException ex) {
            Logger.getLogger(ControllerLOAI.class.getName()).log(Level.SEVERE, null, ex);
        }
        tablesp.setItems(data);
    }

    private void setcellvalueformtableview()    {
        tablesp.setOnMouseClicked(e->{
            SANPHAM p1 = tablesp.getItems().get(tablesp.getSelectionModel().getFocusedIndex());
            txtmasp.setText(p1.getMASP());
            txtten.setText(p1.getTENSP());
            txtvl.setText(p1.getVATLIEU());
            txtthbh.setText(p1.getTHOIHANBAOHANH());
            txtsl.setText(p1.getSLTON());
            txtgia.setText(p1.getGIA());
            txtmatl.setText(p1.getMATL());
            txtmakho.setText(p1.getMAKHO());});
    }
    @FXML
    private void handleAdd(javafx.event.ActionEvent event) throws SQLException {
        if(txtmasp.getText().isEmpty()== false && txtten.getText().isEmpty() == false&& txtvl.getText().isEmpty() == false&& txtthbh.getText().isEmpty() == false&& txtsl.getText().isEmpty() == false&& txtgia.getText().isEmpty() == false&& txtmatl.getText().isEmpty() == false&& txtmakho.getText().isEmpty() == false){
            if(checkPhone( txtsl.getText())==true) {
        String sql = "Insert Into SANPHAM(MASP,TENSP,VATLIEU,THOIHANBAOHANH,SLTON,GIA,MATL,MAKHO) Values(?,?,?,?,?,?,?,?)";
        String masp = txtmasp.getText();
        String ten = txtten.getText();
        String vl = txtvl.getText();
        String thbh = txtthbh.getText();
        String sl = txtsl.getText();
        String gia = txtgia.getText();
        String matl = txtmatl.getText();
        String makho = txtmakho.getText();

        try {
            pst = con.prepareStatement(sql);
            pst.setString(1, masp);
            pst.setString(2, ten);
            pst.setString(3, vl);
            pst.setString(4, thbh);
            pst.setString(5, sl);
            pst.setString(6, gia);
            pst.setString(7, matl);
            pst.setString(8, makho);

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
            Logger.getLogger(ControllerSANPHAM.class.getName()).log(Level.SEVERE, null, ex);
        } finally {

            pst.close();
        }}else{
                Alert VV = new Alert(Alert.AlertType.WARNING, " Số lượng không hợp lệ!", ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
            }
        }else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Cần điền đầy đủ thông tin!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }
    }

    public void handleDelete(javafx.event.ActionEvent event) throws SQLException {
        String sql = "delete from SANPHAM where MASP = ?";

        try{
            pst = con.prepareStatement(sql);
            pst.setString(1,txtmasp.getText());
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
            Logger.getLogger(ControllerSANPHAM.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleUpdate(javafx.event.ActionEvent event) {
        if(txtmasp.getText().isEmpty()== false && txtten.getText().isEmpty() == false&& txtvl.getText().isEmpty() == false&& txtthbh.getText().isEmpty() == false&& txtsl.getText().isEmpty() == false&& txtgia.getText().isEmpty() == false&& txtmatl.getText().isEmpty() == false&& txtmakho.getText().isEmpty() == false){
            if(checkPhone( txtsl.getText())==true) {
        String sql = "Update SANPHAM  set  TENSP=?,VATLIEU=?,THOIHANBAOHANH=?,SLTON=?,GIA=?,MATL=?,MAKHO=? WHERE MASP = ? ";
        try {
            String masp = txtmasp.getText();
            String ten = txtten.getText();
            String vl = txtvl.getText();
            String thbh = txtthbh.getText();
            String sl = txtsl.getText();
            String gia = txtgia.getText();
            String matl = txtmatl.getText();
            String makho = txtmakho.getText();

            pst = con.prepareStatement(sql);

            pst.setString(1, ten);
            pst.setString(2, vl);
            pst.setString(3, thbh);
            pst.setString(4, sl);
            pst.setString(5, gia);
            pst.setString(6, matl);
            pst.setString(7, makho);
            pst.setString(8, masp);

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
            Logger.getLogger(ControllerSANPHAM.class.getName()).log(Level.SEVERE, null, ex);
        }}else{
                Alert VV = new Alert(Alert.AlertType.WARNING, " Số lượng không hợp lệ!", ButtonType.OK);
                VV.initStyle(StageStyle.UNDECORATED);
                VV.showAndWait();
            }
        }else {
            Alert VV = new Alert(Alert.AlertType.WARNING, "Cần điền đầy đủ thông tin!", ButtonType.OK);
            VV.initStyle(StageStyle.UNDECORATED);
            VV.showAndWait();
        }
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
                String sql ="Select *from SANPHAM Where MASP LIKE '%"+txtsearch.getText()+"%'"
                        + "UNION Select *from SANPHAM Where TENSP LIKE N'%"+txtsearch.getText()+"%' "
                        + "UNION Select *from SANPHAM Where VATLIEU LIKE N'%"+txtsearch.getText()+"%'"
                        + "UNION Select *from SANPHAM Where THOIHANBAOHANH LIKE '%"+txtsearch.getText()+"%'"
                        + "UNION Select *from SANPHAM Where SLTON LIKE '%"+txtsearch.getText()+"%'"
                        + "UNION Select *from SANPHAM Where GIA LIKE '%"+txtsearch.getText()+"%'"
                        + "UNION Select *from SANPHAM Where MATL LIKE '%"+txtsearch.getText()+"%'"
                        + "UNION Select *from SANPHAM Where MAKHO LIKE '%"+txtsearch.getText()+"%'";
                try {
                    pst =con.prepareStatement(sql);

                    rs=pst.executeQuery();
                    while (rs.next())
                    {
                        //System.out.println(""+rs.getString(1));
                        data.add(new SANPHAM(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8)));
                    }
                    tablesp.setItems(data);
                } catch (SQLException ex) {
                    Logger.getLogger(ControllerSANPHAM.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        });
    }
    public void CancelButtonOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) btnthoat.getScene().getWindow();
        stage.close();
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
