package crud;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;

public class SimplePut {
    //declaração de bytes estáticos "topo da coluna"
    private static byte[] PERSONAL_CF = Bytes.toBytes("personal");
    private static byte[] PROFESSIONAL_CF = Bytes.toBytes("professional");

    //declaração das matrizes "oq vai ser adcionado"
    private static byte[] NAME_COLUMN = Bytes.toBytes("name");
    private static byte[] GENDER_COLUMN = Bytes.toBytes("gender");
    private static byte[] MARITAL_STATUS_COLUMN = Bytes.toBytes("marital_status");

    //declaração das matrizes dentro da familia de colunas
    private static byte[] EMPLOYED_COLUMN = Bytes.toBytes("employed");
    private static byte[] FIELD_COLUMN = Bytes.toBytes("field");

    public static void main(String[] args) throws IOException {
        //instanciando as configurações pra conexão
        Configuration conf = HBaseConfiguration.create();

        try (Connection connection = ConnectionFactory.createConnection(conf); Table table = connection.getTable(TableName.valueOf("census"))) {
            //criando a tabela

            //Put da biblioteca do hbase que requer um ID
            Put put1 = new Put(Bytes.toBytes("1"));

            //put requer que vc especifique a coluna na qual deseja colocar os dados
            put1.addColumn(PERSONAL_CF, NAME_COLUMN, Bytes.toBytes("Mike Jones"));
            put1.addColumn(PERSONAL_CF, GENDER_COLUMN, Bytes.toBytes("male"));
            put1.addColumn(PERSONAL_CF, MARITAL_STATUS_COLUMN, Bytes.toBytes("unmarried"));
            //valores da familia da coluna
            put1.addColumn(PROFESSIONAL_CF, EMPLOYED_COLUMN, Bytes.toBytes("yes"));
            put1.addColumn(PROFESSIONAL_CF, FIELD_COLUMN, Bytes.toBytes("construction"));

            table.put(put1);
            System.out.println("Inserted row for Mike Jones");
            /* isnserido o primeiro dado de forma simples*/

            //inserindo com Array
            Put put2 = new Put(Bytes.toBytes("2"));

            put2.addColumn(PERSONAL_CF, NAME_COLUMN, Bytes.toBytes("Hillary Clinton"));
            put2.addColumn(PERSONAL_CF, GENDER_COLUMN, Bytes.toBytes("female"));
            put2.addColumn(PERSONAL_CF, MARITAL_STATUS_COLUMN, Bytes.toBytes("married"));

            put2.addColumn(PROFESSIONAL_CF, FIELD_COLUMN, Bytes.toBytes("politics"));

            Put put3 = new Put(Bytes.toBytes("3"));

            put3.addColumn(PERSONAL_CF, NAME_COLUMN, Bytes.toBytes("Donald Trump"));
            put3.addColumn(PERSONAL_CF, GENDER_COLUMN, Bytes.toBytes("male"));

            put3.addColumn(PROFESSIONAL_CF, FIELD_COLUMN, Bytes.toBytes("politics, real estate"));

            /* isnserido mais de um dado*/
            ArrayList<Put> putList = new ArrayList<>();
            putList.add(put2);
            putList.add(put3);

            table.put(putList);

            System.out.println("Inserted rows for Hillary Clinton and Donald Trump");
        }
    }
}