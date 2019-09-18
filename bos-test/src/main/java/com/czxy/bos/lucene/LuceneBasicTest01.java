package com.czxy.bos.lucene;

import junit.framework.TestFailure;
import org.apache.catalina.Store;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @ClassName LuceneBasicTest01
 * @Author 宋明明
 * @Date 2018/9/27 18:16
 * Version 1.0
 **/
public class LuceneBasicTest01 {

    @Test
    public void testCreat() throws IOException {
        //1 创建文档对象
        final Document document = new Document();
        document.add(new StringField("id","1",Field.Store.YES));
        document.add(new TextField("title","谷歌地图之父跳槽Facebook",Field.Store.YES));
        //2 创建存储目录
        final FSDirectory directory = FSDirectory.open(new File("d://indexDir"));
        //3 创建分词器
        final StandardAnalyzer analyzer = new StandardAnalyzer();
        //4 创建索引写入器的配置对象
        final IndexWriterConfig writerConfig = new IndexWriterConfig(Version.LATEST, analyzer);
        //5 创建索引写入器对象
        final IndexWriter indexWriter = new IndexWriter(directory, writerConfig);
        //6 将文档交给索引写入器
        indexWriter.addDocument(document);
        //7 提交
        indexWriter.commit();
        //8 关闭
        indexWriter.close();

    }

     /**
       *功能描述:
       * @auther: 宋明明
       * @date: 2018/9/27 20:01
       * @return:
      * 测试存储
       **/
    @Test
    public void testCreatIndex02()throws Exception{
        //1 创建文档对象
        Document document = new Document();
        //2 创建Field字段
        //对于编号，可以使用DoubleField、IntField、FloatField、StringField...
        //第一个参数：对应mysql中的字段名字
        //第二个参数：该字段的值
        //第三个参数：是否存储
        document.add(new StringField("id","1",Field.Store.YES));
        //对于title或者content等文件较多的内容，可以使用TextField
        document.add(new TextField("title","谷歌地图之父跳槽Facebook",Field.Store.YES));


        //3 创建索引目录对象:文件内容的存储，可以存储在硬盘上，也可以存储在内存中
        // 如果存储在硬盘上，使用FSDirectory
        //          内存中，使用RAMDirectory
        FSDirectory directory = FSDirectory.open(new File("d:\\indexDir"));
        //4 创建分词器
        StandardAnalyzer analyzer = new StandardAnalyzer();
        //5 创建索引写出器配置对象
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST, analyzer);
        //6 创建索引写出器对象
        IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        //7 写出索引，将索引写入索引库
        indexWriter.addDocument(document);
        //8 提交
        indexWriter.commit();
        //9 关闭
        indexWriter.close();

        System.out.println("okok.....");

    }

    /**
     * 测试存储
     * @throws Exception
     */
    @Test
    public void testCreateIndex02() throws  Exception{
        //1 创建文档对象
        final Document document = new Document();
        //2 创建Field字段
        //对于编号，可以使用DoubleField、IntField、FloatField、StringField...
        //第一个参数：对应mysql中的字段名字
        //第二个参数：该字段的值
        //第三个参数：是否存储
        document.add(new StringField("id","1",Field.Store.YES));
        //对于title或者content等文件较多的内容，可以使用TextField
        document.add(new TextField("title","谷歌地图之父跳槽Facebook",Field.Store.YES));
        // 测试存储
        document.add(new StoredField("content","热烈欢迎谷歌地图之父拉斯加入到了Facebook，可助Facebook一臂之力"));

        //3 创建索引目录对象:文件内容的存储，可以存储在硬盘上，也可以存储在内存中
        // 如果存储在硬盘上，使用FSDirectory
        //          内存中，使用RAMDirectory
        final FSDirectory directory = FSDirectory.open(new File("d://indexDir"));
        //4 创建分词器
        final StandardAnalyzer analyzer = new StandardAnalyzer();
        //5 创建索引写出器配置对象
        final IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST, analyzer);
        //6 创建索引写出器对象
        final IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        //7 写出索引，将索引写入索引库
        indexWriter.addDocument(document);
        //8 提交
        indexWriter.commit();
        //9 关闭
        indexWriter.close();
        System.out.println("okok.....");
    }

    /**
     * 测试中文IK分词器
     * @throws Exception
     */
    @Test
    public void testCreatIndex03() throws Exception{
        //1 创建文档对象
        final Document document = new Document();
        //2 创建Field字段
        //对于编号，可以使用DoubleField、IntField、FloatField、StringField...
        //第一个参数：对应mysql中的字段名字
        //第二个参数：该字段的值
        //第三个参数：是否存储
        document.add(new StringField("id","1",Field.Store.YES));
        //对于title或者content等文件较多的内容，可以使用TextField
        document.add(new TextField("title","李开复加入了传智大学,厉害了",Field.Store.YES));
        // 测试存储
        //document.add(new TextField("content","热烈欢迎谷歌地图之父拉斯加入到了Facebook，可助Facebook一臂之力"));

        //3 创建索引目录对象:文件内容的存储，可以存储在硬盘上，也可以存储在内存中
        // 如果存储在硬盘上，使用FSDirectory
        //          内存中，使用RAMDirectory
        final FSDirectory directory = FSDirectory.open(new File("d://indexDir"));
        //4 创建分词器
        //StandardAnalyzer analyzer = new StandardAnalyzer();
        // 换成IK分词器
        final IKAnalyzer ikAnalyzer = new IKAnalyzer();
        //5 创建索引写出器配置对象
        final IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST, ikAnalyzer);
        //OpenMode.CREATE:清空原来的索引。重新创建
        //OpenMode.APPEND:在原来的基础上追加
        //OpenMode.CREATE_OR_APPEND:不存在，新建；存在，追加
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);

        //6 创建索引写出器对象
        final IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        //7 写出索引，将索引写入索引库
        indexWriter.addDocument(document);
        //8 提交
        indexWriter.commit();
        //9 关闭
        indexWriter.close();

        System.out.println("okok.....");

    }

    /**
     * 批量创建索引
     * @throws Exception
     */
    @Test
    public void testCreateIndex04() throws  Exception{
        final ArrayList<Document> docs = new ArrayList<>();
        //1 创建文档对象
//        Document d1 = new Document();
//        d1.add(new StringField("id","1",Field.Store.YES));
//        d1.add(new TextField("title","谷歌地图之父跳槽Facebook",Field.Store.YES));
//
//        Document d2 = new Document();
//        d2.add(new StringField("id","2",Field.Store.YES));
//        d2.add(new TextField("title","谷歌地图之父加盟Facebook",Field.Store.YES));
//
//        Document d3 = new Document();
//        d3.add(new StringField("id","3",Field.Store.YES));
//        d3.add(new TextField("title","谷歌地图创始人拉斯离开谷歌加盟Facebook",Field.Store.YES));
//
//        Document d4 = new Document();
//        d4.add(new StringField("id","4",Field.Store.YES));
//        d4.add(new TextField("title","谷歌地图之父跳槽Facebook与Wave项目取消有关",Field.Store.YES));
//
//        Document d5 = new Document();
//        d5.add(new StringField("id","5",Field.Store.YES));
//        d5.add(new TextField("title","谷歌地图之父拉斯加盟社交网站Facebook",Field.Store.YES));

        Document d1 = new Document();
        d1.add(new LongField("id",1l,Field.Store.YES));
        d1.add(new TextField("title","谷歌地图之父跳槽Facebook",Field.Store.YES));

        Document d2 = new Document();
        d2.add(new LongField("id",2l,Field.Store.YES));
        d2.add(new TextField("title","谷歌地图之父加盟Facebook",Field.Store.YES));

        Document d3 = new Document();
        d3.add(new LongField("id",3l,Field.Store.YES));
        d3.add(new TextField("title","谷歌地图创始人拉斯离开谷歌加盟Facebook",Field.Store.YES));

        Document d4 = new Document();
        d4.add(new LongField("id",4l,Field.Store.YES));
        TextField field = new TextField("title", "谷歌地图之父跳槽Facebook与Wave项目取消有关", Field.Store.YES);
//        field.setBoost(10);
        d4.add(field);

        Document d5 = new Document();
        d5.add(new LongField("id",5l,Field.Store.YES));
        d5.add(new TextField("title","谷歌地图之父拉斯加盟社交网站Facebook",Field.Store.YES));

        docs.add(d1);
        docs.add(d2);
        docs.add(d3);
        docs.add(d4);
        docs.add(d5);

        final FSDirectory directory = FSDirectory.open(new File("d://indexDir"));
        final IKAnalyzer analyzer = new IKAnalyzer();
        final IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LATEST, analyzer);
        indexWriterConfig.setOpenMode(IndexWriterConfig.OpenMode.CREATE);
        final IndexWriter indexWriter = new IndexWriter(directory, indexWriterConfig);
        indexWriter.addDocuments(docs);
        indexWriter.commit();
        indexWriter.close();
        System.out.println("okok.....");

    }

    /*****************查询******************/
    @Test
    public void testSearch01() throws Exception{
        //1 创建索引目录对象---索引在硬盘，读取
        final FSDirectory directory = FSDirectory.open(new File("d://indexDir"));
        //2 创建目录读取工具--通过工具读取目录中的内容
        final DirectoryReader reader = DirectoryReader.open(directory);
        //3 创建索引搜索工具--通过搜索工具搜索内容
        final IndexSearcher searcher = new IndexSearcher(reader);
        //4 创建查询解析器--查询哪一列？这一列有什么特殊要求？
        // 第一个参数：搜索的列名
        // 第二个参数：分词器
//        QueryParser parser = new QueryParser("title",new IKAnalyzer());
        // 查询多列
        final MultiFieldQueryParser queryParser = new MultiFieldQueryParser(new String[]{"id", "title", "content"}, new IKAnalyzer());


        //5 创建查询对象--从title中搜索什么内容
        final Query query = queryParser.parse("谷歌");
        //6 搜索数据--执行查询
        // 第一个参数：查询对象
        // 第二个参数：结果条数
        // 返回值： 只包含两个结果：1 击中数   2 集中的文档编号(此编号是lucene给文档分配的编号)
        final TopDocs topDocs = searcher.search(query, 10);
        //7 各种操作--获取/解析  查询结果
        final int totalHits = topDocs.totalHits;//命中数   “谷歌”5   “加盟”1
        System.out.println("命中次数/击中次数:"+totalHits);
        //查找出得分最高的10个，把得分最高的几个结果显示在最上面，获取这些数据
        final ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        //遍历结果
        for (ScoreDoc scoreDoc : scoreDocs) {
            final int doc = scoreDoc.doc;
            //通过sd获取文档编号
            //获取得分

            // 通过文档编号  获取 文档内容;借助读取工具读取文档
            final Document document = reader.document(doc);
            // 返回文档
            System.out.println(document.get("id")+":");
            System.out.println(document.get("title")+":"+"得分:"+scoreDoc.score);
        }

    }}
