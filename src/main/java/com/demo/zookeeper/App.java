package com.demo.zookeeper;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.concurrent.CountDownLatch;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception
    {
        System.out.println( "Hello World!" );
        //zk是有session概念的，没有连接池的概念
        // watch:观察、回调
        // watch的注册值发生在 读类型调用。 get、exists..
        //第一类： new zk 时候，这个watch，session级别的，跟path、node没有关系。
        final CountDownLatch cd = new CountDownLatch(1);
        final ZooKeeper zk = new ZooKeeper("192.168.82.255:2181,192.168.82.255:2181",
                3000, new Watcher() {
            //Watch的回调方法！
            @Override
            public void process(WatchedEvent event) {
                Event.KeeperState state = event.getState();
                Event.EventType type = event.getType();
                String path = event.getPath();
                System.out.println("new zk watch: "+event.toString());

                switch (state) {
                    case Unknown:
                        break;
                    case Disconnected:
                        break;
                    case NoSyncConnected:
                        break;
                    case SyncConnected:
                        System.out.println("connected");
                        //cd.countDown();
                        break;
                    case AuthFailed:
                        break;
                    case ConnectedReadOnly:
                        break;
                    case SaslAuthenticated:
                        break;
                    case Expired:
                        break;
                }

                switch (type) {
                    case None:
                        break;
                    case NodeCreated:
                        break;
                    case NodeDeleted:
                        break;
                    case NodeDataChanged:
                        break;
                    case NodeChildrenChanged:
                        break;
                }


            }
        });

        //cd.await();
        ZooKeeper.States state = zk.getState();
        switch (state) {
            case CONNECTING:
                System.out.println("CONNECTING........");
                break;
            case ASSOCIATING:
                break;
            case CONNECTED:
                System.out.println("CONNECTED..........");
                break;
            case CONNECTEDREADONLY:
                break;
            case CLOSED:
                break;
            case AUTH_FAILED:
                break;
            case NOT_CONNECTED:
                break;
        }

        // 创建数据
        String pathName = zk.create("/ooxx", "olddata".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);

        // 获取数据
        final Stat stat = new Stat();
        byte[] node = zk.getData("/ooxx", new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("getData watch:" + event.toString());
                try {
                    //true default Watch 被重新注册  new zk的那个watch
                    zk.getData("/ooxx", this, stat);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, stat);
        System.out.println(new String(node));

        //触发回调
        Stat stat1 = zk.setData("/ooxx", "newdata".getBytes(), 0);

        Stat stat2 = zk.setData("/ooxx", "newdata01".getBytes(), stat1.getVersion());

        System.out.println("--------asyne start--------");
        zk.getData("/ooxx", false, new AsyncCallback.DataCallback() {
            @Override
            public void processResult(int rc, String path, Object ctx, byte[] data, Stat stat) {
                System.out.println("--------asyne call back--------");
                System.out.println(ctx.toString());
                System.out.println(data.toString());
            }
        },"abc");
        System.out.println("--------asyne over--------");

        Thread.sleep(2222222);
    }
}
