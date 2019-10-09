package mapreduce;

import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.IntWritable;

import java.io.IOException;

//Nossa classe é uma deriva/extensão de uma classe generica TableReducer da bliblioteca do HBase
public class MaritalStatusReducer extends
        TableReducer<ImmutableBytesWritable, IntWritable, ImmutableBytesWritable> {
    //ImmutableBytesWritable é a nossa chave de saída e o tipo de dados de nosso valor de saída
    //IntWritable é um tipo wrapper  onde a chave é uma matriz de bytes
    //o terceiro parametro generico é do tipo de dados da chave de saida que vamos gerar depois de rodar

    //Para onde vamos envuar os dados
    private static final byte[] CF = "marital_status".getBytes();
    private static final byte[] COUNT = "count".getBytes();

    //vamos combinar os vários vaores associados à mesma chave
    public void reduce(ImmutableBytesWritable key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
        //os dois primeiros parametros são os pares de valor-chave de saída
        //o ultimo é o contexto, nossa ponte para fora é onde é gravada nesse contexto

        //vamos contar o número de registros associados a cada categoria
        Integer sum = 0;
        for (IntWritable val : values) {
            sum += val.get();
        }

        //Inicializar um novo put com a mesma chave que foi passada
        Put put = new Put(key.get());
        put.addColumn(CF, COUNT, Bytes.toBytes(sum.toString()));

        //os resultados serão gravados no context e vão se aplicados à tabela
        context.write(key, put);
    }
}
