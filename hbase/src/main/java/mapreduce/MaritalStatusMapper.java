package mapreduce;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableMapper;
import org.apache.hadoop.io.IntWritable;
import java.io.IOException;

//Nossa classe é uma deriva/extensão de uma classe generica TableMapper da bliblioteca do HBase
public  class MaritalStatusMapper extends
        TableMapper<ImmutableBytesWritable, IntWritable> {
        //ImmutableBytesWritable é a nossa chave de saída e o tipo de dados de nosso valor de saída
        //IntWritable é um tipo wrapper  onde a chave é uma matriz de bytes

    //vamos extrair os valores da coluna
    private static final byte[] CF = "personal".getBytes();
    private static final byte[] MARITAL_STATUS = "marital_status".getBytes();

    //vamos associar uma contagem de 1 para cada processo gravado que tenha o campo buscado
    private final IntWritable ONE = new IntWritable(1);

    //para cada registro analisado tera uma chave diferente
    private ImmutableBytesWritable key = new ImmutableBytesWritable();

    //Esse metodo processa um registro da tabela Hbase por vez
    //os parametros são do par de valores-chaves de entrada. O ultimo parametro é parametro para o mundo exterior
    public void map(ImmutableBytesWritable row, Result value, Context context) throws IOException, InterruptedException {

        //o get extrai o valor/chave da coluna e o set o define na chamada
        key.set(value.getValue(CF, MARITAL_STATUS));

        //os valores que foram gravados no context associados a uma chave
        context.write(key, ONE);
    }
}
