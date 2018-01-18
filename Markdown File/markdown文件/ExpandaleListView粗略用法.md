## ExpandableListView的简单用法
***
* 首先在xml文件中加入ExpandableListView，为了避免使用ScrollView导致的显示不全问题，我们重写一下ExpandableListView，然后在xml中加入重写的ExpandableListView，重写的ExpandableListView如下所示：

      public class NestedExpandaleListView extends ExpandableListView {
        public NestedExpandaleListView(Context context, AttributeSet attrs) {
          super(context, attrs);
        }

        @Override
        protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

          int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                  MeasureSpec.AT_MOST);

          //将重新计算的高度传递回去
          super.onMeasure(widthMeasureSpec, expandSpec);
        }
      }
然后xml布局如下：
      <com.hoshi.graduationproject.mymusic.NestedExpandaleListView
          android:id="@+id/expandablelistview"
          android:layout_width="match_parent"
          android:layout_height="wrap_content"
          android:background="@color/lite_gray"
          android:divider="@null"
          android:cacheColorHint="#00000000"
          android:listSelector="#00000000">
      </com.hoshi.graduationproject.mymusic.NestedExpandaleListView>

  自定义listview的时候，当你不使用android:cacheColorHint=“#00000000”会出现下面选中一个空间黑色底色的情况，破坏整体美观度：

  ![Alt text](http://ogtt75s5d.bkt.clouddn.com/ExpandableListView%E5%9B%BE%E7%89%871.jpg)

  当你不使用android:listSelector属性，默认会显示选中的item为橙黄底色，有时候我们需要去掉这种效果：
  ![Alt text](http://ogtt75s5d.bkt.clouddn.com/ExpandableListView%E5%9B%BE%E7%89%872.jpg)
* 然后在java文件中声明绑定这个ExpandaleListView：
      private NestedExpandaleListView mExpandableListView;
      mExpandableListView = (NestedExpandaleListView) getActivity().findViewById(R.id.expandablelistview);
* 重写MyExpandableListViewAdapter继承自BaseExpandableListAdapter：
      class MyExpandableListViewAdapter extends BaseExpandableListAdapter {

          private Context context;

          public MyExpandableListViewAdapter(Context context) {
            this.context = context;
          }

          @Override
          public int getGroupCount() {
            return group_list.size();
          }

          @Override
          public int getChildrenCount(int groupPosition) {
            return item_list.get(groupPosition).size();
          }

          @Override
          public Object getGroup(int groupPosition) {
            return group_list.get(groupPosition);
          }

          @Override
          public Object getChild(int groupPosition, int childPosition) {
            return item_list.get(groupPosition).get(childPosition);
          }

          @Override
          public long getGroupId(int groupPosition) {
            return groupPosition;
          }

          @Override
          public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
          }

          @Override
          public boolean hasStableIds() {
            return true;
          }

          @Override
          public View getGroupView(int groupPosition, boolean isExpanded,
                                   View convertView, ViewGroup parent) {
            GroupHolder groupHolder = null;
            if (convertView == null) {
              convertView = (View) getActivity().getLayoutInflater().from(context).inflate(
                      R.layout.fragment_mymusic_expendlist_group, null);
              groupHolder = new GroupHolder();
              groupHolder.group_text = (TextView) convertView.findViewById(R.id.group_text);
              groupHolder.group_arrow_right = (ImageView) convertView.findViewById(R.id.group_arrow_right);
              groupHolder.group_arrow_down = (ImageView) convertView.findViewById(R.id.group_arrow_down);
              convertView.setTag(groupHolder);
            } else {
              groupHolder = (GroupHolder) convertView.getTag();
            }
            groupHolder.group_text.setText(group_list.get(groupPosition));

            if (isExpanded) {
              groupHolder.group_arrow_right.setVisibility(View.GONE);
              groupHolder.group_arrow_down.setVisibility(View.VISIBLE);
            } else {
              groupHolder.group_arrow_right.setVisibility(View.VISIBLE);
              groupHolder.group_arrow_down.setVisibility(View.GONE);
            }

            return convertView;
          }

          @Override
          public View getChildView(int groupPosition, int childPosition,
                                   boolean isLastChild, View convertView, ViewGroup parent) {
            ItemHolder itemHolder = null;
            if (convertView == null) {
              convertView = (View) getActivity().getLayoutInflater().from(context).inflate(
                      R.layout.fragment_mymusic_expendlist_item, null);
              itemHolder = new ItemHolder();
              itemHolder.item_text = (TextView) convertView.findViewById(R.id.item_text);
              convertView.setTag(itemHolder);
            } else {
              itemHolder = (ItemHolder) convertView.getTag();
            }
            itemHolder.item_text.setText(item_list.get(groupPosition).get(
                    childPosition));
            return convertView;
          }

          @Override
          public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
          }
        }
其中的getGroupView()和getChildView()方法中分别inflate了父级布局和子级布局，然后分别用两个holder来加载布局中的元素，并且父级布局通过isExpanded来进行判断，实现了展开和合上时不同的箭头指向，下面是两个holder：
      class GroupHolder {
        public TextView group_text;
        public ImageView group_arrow_right;
        public ImageView group_arrow_down;
      }

      class ItemHolder {
        public TextView item_text;
      }
在对ExpandableListView进行findViewById之后对内容进行初始化如下：
      protected void initView() {
        group_list = new ArrayList<String>();
        item_list = new ArrayList<List<String>>();

        second_group_list = new ArrayList<String>();
        for (int j = 0; j < 5; j++) {
          second_group_list.add("123");
        }
        group_list.add("我创建的歌单");
        item_list.add(second_group_list);

        second_group_list = new ArrayList<String>();
        for (int j = 0; j < 5; j++) {
          second_group_list.add("123");
        }
        group_list.add("我收藏的歌单");
        item_list.add(second_group_list);

        mExpandableListView.setAdapter(new MyExpandableListViewAdapter(rootView.getContext()));
        mExpandableListView.setGroupIndicator(null);  //将默认的左箭头去掉

        //点击事件
        mExpandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

          @Override
          public boolean onChildClick(ExpandableListView parent, View v,
                                      int groupPosition, int childPosition, long id) {
            /*Intent pregEncDetailIntent = new Intent(getActivity(), PregEncDetailActivity.class);
            pregEncDetailIntent.putExtra(RequestKeys.ID, bKData[groupPosition].smallData[childPosition].id);
            pregEncDetailIntent.putExtra(RequestKeys.BIGCONTENT, bKData[groupPosition].smallData[childPosition].content);
            startActivity(pregEncDetailIntent);*/
            return true;
          }
        });

        mExpandableListView.setVisibility(View.VISIBLE);
      }
