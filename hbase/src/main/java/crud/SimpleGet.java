package crud;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;
import java.io.IOException;
import java.util.ArrayList;


public class SimpleGet {
    //declaração de bytes estáticos "topo da coluna"
    private static byte[] PERSONAL_CF = Bytes.toBytes("personal");
    private static byte[] PROFESSIONAL_CF = Bytes.toBytes("professional");

    //declaração das matrizes dentro da familia de colunas
    private static byte[] NAME_COLUMN = Bytes.toBytes("name");
    private static byte[] FIELD_COLUMN = Bytes.toBytes("field");

    public static void main(String[] args) throws IOException {
        //instanciando as configurações pra conexão
        Configuration conf = HBaseConfiguration.create();

        try (Connection connection = ConnectionFactory.createConnection(conf) ; Table table = connection.getTable(TableName.valueOf("census"))) {
            //criando a tabela

            //Get da biblioteca do hbase que requer um ID
            Get get = new Get(Bytes.toBytes("1"));

            get.addColumn(PERSONAL_CF, NAME_COLUMN);
            get.addColumn(PROFESSIONAL_CF, FIELD_COLUMN);

            //o get retorna um objeto de resultado,
            Result result = table.get(get);
            // esse resultado representa o registro da tabela

            //convertendo o dado para ser exibido
            byte[] nameValue = result.getValue(PERSONAL_CF, NAME_COLUMN);
            System.out.println("Name: " + Bytes.toString(nameValue));

            byte[] fieldValue = result.getValue(PROFESSIONAL_CF, FIELD_COLUMN);
            System.out.println("Field: " + Bytes.toString(fieldValue));

            System.out.println();
            System.out.println("SimpleGet multiple results in one go:");
            /* buscado o primeiro dado de forma simples*/

            //Buscando com Array
            ArrayList<Get> getList = new ArrayList<>();
            //obter varios registro ao mesmo tempo
            Get get1 = new Get(Bytes.toBytes("2"));
            //recuperar os registros das chaves de linha
            get1.addColumn(PERSONAL_CF, NAME_COLUMN);

            Get get2 = new Get(Bytes.toBytes("3"));
            get2.addColumn(PERSONAL_CF, NAME_COLUMN);

            //fazer a associação dos registros
            getList.add(get1);
            getList.add(get2);

            //Instanciar a lista e chamar a tabela
            Result[] results = table.get(getList);
            //o for vai percorrer os registros e exibilos já convertidos em string
            for (Result res : results) {
                nameValue = res.getValue(PERSONAL_CF, NAME_COLUMN);
                System.out.println("Name: " + Bytes.toString(nameValue));
            }
        }
    }
}