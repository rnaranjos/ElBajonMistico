package com.example.ElBajonMistico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private EditText edcodigo,ednombre,edprecio,edingredientes;
    private RadioButton crearp,buscap,eliminap,modificap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edcodigo=findViewById(R.id.codigo);
        ednombre=findViewById(R.id.nombre);
        edprecio=findViewById(R.id.precio);
        edingredientes=findViewById(R.id.ingredientes);
    }
    public void crearProducto(View v){
        admindb admin= new admindb( this,"Productos",null,1);
        SQLiteDatabase base=admin.getWritableDatabase();

        String codigo=edcodigo.getText().toString();
        String nombre=ednombre.getText().toString();
        String precio=edprecio.getText().toString();
        String ingredientes=edingredientes.getText().toString();

        if(!codigo.isEmpty() && !nombre.isEmpty() && !precio.isEmpty() && !ingredientes.isEmpty()){
            ContentValues crear=new ContentValues();
            crear.put("codigo",codigo);
            crear.put("nombre",nombre);
            crear.put("precio",precio);
            crear.put("ingredientes",ingredientes);

            //Falta crear validador de existencia de producto/

            base.insert("producto",null,crear);
            base.close();
            edcodigo.setText("");
            ednombre.setText("");
            edprecio.setText("");
            edingredientes.setText("");
            Toast.makeText(this, "Registro creado", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Debe completar todos los campos",Toast.LENGTH_LONG).show();
        }

    }
    public void buscarProducto(View v){
        admindb admin= new admindb( this,"Productos",null,1);
        SQLiteDatabase base=admin.getWritableDatabase();

        String codigo=edcodigo.getText().toString();

        if(!codigo.isEmpty()){
            Cursor fila=base.rawQuery("select nombre,precio,ingredientes from producto where codigo="+codigo,null);

            if(fila.moveToFirst()){
                ednombre.setText(fila.getString(0));
                edprecio.setText(fila.getString(1));
                edingredientes.setText(fila.getString(2));

                base.close();
            }else  {
                Toast.makeText(this,"El producto no existe",Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(this,"Debe ingresar un código de producto",Toast.LENGTH_LONG).show();
        }

    }
    public void modificarProducto(View v){
        admindb admin= new admindb( this,"Productos",null,1);
        SQLiteDatabase base=admin.getWritableDatabase();

        String codigo=edcodigo.getText().toString();
        String nombre=ednombre.getText().toString();
        String precio=edprecio.getText().toString();
        String ingredientes=edingredientes.getText().toString();
        if(!codigo.isEmpty() && !nombre.isEmpty() && !precio.isEmpty() && !ingredientes.isEmpty()){

            ContentValues modificar=new ContentValues();
            modificar.put("codigo",codigo);
            modificar.put("nombre",nombre);
            modificar.put("precio",precio);
            modificar.put("ingredientes",ingredientes);

            base.update("producto", modificar, "codigo="+codigo,null);

            base.close();
            edcodigo.setText("");
            ednombre.setText("");
            edprecio.setText("");
            edingredientes.setText("");
            Toast.makeText(this, "Registro modificado", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Debes llenar todos los campos",Toast.LENGTH_LONG).show();
        }
    }
    public void eliminarProducto(View v){
        admindb admin= new admindb( this,"Productos",null,1);
        SQLiteDatabase base=admin.getWritableDatabase();

        String codigo=edcodigo.getText().toString();
        if(!codigo.isEmpty()){

            base.delete("producto", "codigo="+codigo,null);

            base.close();
            edcodigo.setText("");

            Toast.makeText(this, "Registro eliminado", Toast.LENGTH_LONG).show();
        }else {
            Toast.makeText(this,"Código inexistente",Toast.LENGTH_LONG).show();
        }}}
