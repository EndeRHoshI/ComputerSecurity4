## Android ListView使用模板
***
ListView是Android应用开发中常用的一个组件，写毕业设计时又要用到，但是以前在项目中使用总是使用现成的代码，现在梳理一下到底要如何使用ListView

1. 首先写好每一个item的布局，如R.layout.listview_songs.xml
2. 定义一个类去装载数据，如下：
        public class songList {
          String name;
          int id;
          int index;
          String alias;
          String singer;

          public songList (String name, int id, int index, String alias, String singer) {
            this.name = name;
            this.id = id;
            this.index = index;
            this.alias = alias;
            this.singer = singer;
          }
        }
3. 然后需要写一个adapter来装入数据，如下：
        public class rankListSongAdapter extends BaseAdapter {

          private List<songList> mData;//定义数据。
          private LayoutInflater mInflater;//定义Inflater,加载我们自定义的布局。

          //定义构造器，在Activity创建对象Adapter的时候
          //将数据data和Inflater传入自定义的Adapter中进行处理。
          public rankListSongAdapter(LayoutInflater inflater, List<songList> data){
            mInflater = inflater;
            mData = data;
          }

          @Override
          public int getCount() {
            return mData.size();
          }

          @Override
          public Object getItem(int position) {
            return position;
          }

          @Override
          public long getItemId(int position) {
            return position;
          }

          @Override
          public View getView(int position, View convertview, ViewGroup viewGroup) {
            //获得ListView中的view
            View viewSongList = mInflater.inflate(R.layout.listview_songs,null);
            //获得排行榜歌曲对象
            songList mSongList = mData.get(position);
            //获得自定义布局中每一个控件的对象。
            TextView name = (TextView) viewSongList.findViewById(R.id.rank_songList_name);
            TextView alias = (TextView) viewSongList.findViewById(R.id.rank_songList_alias);
            TextView name_singer = (TextView) viewSongList.findViewById(R.id.rank_songList_name_singer);
            TextView index = (TextView) viewSongList.findViewById(R.id.rank_index);
            //将数据一一添加到自定义的布局中。
            name.setText(mSongList.name);
            if (!mSongList.alias.equals("") && mSongList.alias != null) {
              alias.setText("（" + mSongList.alias + "）");
            } else {
              alias.setVisibility(View.INVISIBLE);
            }
            name_singer.setText(mSongList.singer + " - " + mSongList.name);
            if (mSongList.index < 10) {
              index.setText("0" + mSongList.index);
            }
            else {
              index.setText("" + mSongList.index);
            }
            return viewSongList;
          }
        }
4.在获得数据时将数据填入到List中去，如下：
        songList tempSongList = new songList(name, id, (i + 1), alias, singer);
        mSongList.add(tempSongList);
5.声明这个ListView并且初始化它，然后设置adapter，如下：
        ListView lv_rankSongList;
        lv_rankSongList = (ListView) findViewById(R.id.rank_songList);
        lv_rankSongList.setAdapter(new rankListSongAdapter(getLayoutInflater(), mSongList));
完成
