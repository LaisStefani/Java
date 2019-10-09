package filtros;
import org.apache.commons.lang.time.DateUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FilterMultiple {

    private static void printResults(ResultScanner scanResult) {
        System.out.println();
        System.out.println("Results: ");
        for (Result res : scanResult) {
            for (Cell cell : res.listCells()) {
                String row = new String(CellUtil.cloneRow(cell));
                String family = new String(CellUtil.cloneFamily(cell));
                String column = new String(CellUtil.cloneQualifier(cell));
                String value = new String(CellUtil.cloneValue(cell));

                System.out.println(row + " " + family + " " + column + " " + value);
            }
        }
    }


    public static void main(String[] args) throws IOException {
        Configuration conf = HBaseConfiguration.create();
        Connection connection = ConnectionFactory.createConnection(conf);

        Table table = null;
        ResultScanner scanResult = null;
        //buscar um registro com dois parametros: estado civil e sexo
        try {
            table = connection.getTable(TableName.valueOf("census"));

            SingleColumnValueFilter maritalStatusFilter = new SingleColumnValueFilter(
                    Bytes.toBytes("personal"),
                    Bytes.toBytes("marital_status"),
                    CompareFilter.CompareOp.EQUAL,
                    new BinaryComparator(Bytes.toBytes("divorced")));
            maritalStatusFilter.setFilterIfMissing(true);

            SingleColumnValueFilter genderFilter = new SingleColumnValueFilter(
                    Bytes.toBytes("personal"),
                    Bytes.toBytes("gender"),
                    CompareFilter.CompareOp.EQUAL,
                    new BinaryComparator(Bytes.toBytes("male")));
            genderFilter.setFilterIfMissing(true);

            //lista de filtros pois são mais de uma busca
            List<Filter> filterList = new ArrayList<Filter>();
            filterList.add(maritalStatusFilter);
            filterList.add(genderFilter);
            //novo objeto da classe filterlist
            FilterList filters = new FilterList(filterList);

            //Estanciando a varredura
            Scan userScan = new Scan();
            userScan.setFilter(filters);

            //mostra o resultado da pesquisa
            scanResult = table.getScanner(userScan);
            printResults(scanResult);
        } finally {
            connection.close();
            if (table != null) {
                table.close();
            }
            if (scanResult != null) {
                scanResult.close();
            }
        }

    }
}