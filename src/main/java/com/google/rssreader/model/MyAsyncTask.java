package com.google.rssreader.model;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.rssreader.R;
import com.google.rssreader.WebViewInfo;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MyAsyncTask extends AsyncTask<String, Void, Void> {

    ArrayList<Data> days = null;
    DataAdapter mArrayAdapter;
    ProgressDialog pDialog;
    NodeList nodelist;
    Context context;
    ListView list;

    private static final String KEY_VALUE = "key_value";
    private static final String TAG_TITLE = "title";
    private static final String TAG_AUTHOR = "author";
    private static final String TAG_PUBLICATION_DATE = "pubDate";
    private static final String TAG_LINK = "link";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_ITEM = "item";
    private static final String TAG_IMAGE = "img";
    private static final String TAG_IMAGE_SRC = "src";
    private static final String TAG_ROOT_BEGIN = "<root>";
    private static final String TAG_ROOT_END = "</root>";


    public MyAsyncTask(Context context, ListView list) {
        this.list = list;
        this.context = context;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setTitle(context.getResources().getString(R.string.titleName));
        pDialog.setMessage(context.getResources().getString(R.string.loading));
        pDialog.setIndeterminate(false);
        pDialog.show();
    }

    @Override
    protected Void doInBackground(String... Url) {
        try {
            URL url = new URL(Url[0]);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            // Download the XML file
            Document doc = db.parse(new InputSource(url.openStream()));
            doc.getDocumentElement().normalize();
            // Locate the Tag Name item
            nodelist = doc.getElementsByTagName(TAG_ITEM);

        } catch (Exception e) {
            Toast toast = Toast.makeText(context,
                    e.getMessage(), Toast.LENGTH_LONG);
            toast.show();
        }
        return null;

    }

    @Override

    protected void onPostExecute(Void args) {
        days = new ArrayList<>();
        Parser parser = new Parser();

        try {
            for (int temp = 0; temp < nodelist.getLength(); temp++) {
                Node nNode = nodelist.item(temp);
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;

                    NodeList nlCoverURL = eElement.getElementsByTagName(TAG_DESCRIPTION);
                    Node nCoverURL = nlCoverURL.item(0);
                    String sCoverURL = nCoverURL.getTextContent();
                    Document document = parser.getDomElement(TAG_ROOT_BEGIN + sCoverURL + TAG_ROOT_END);
                    NodeList nList = document.getElementsByTagName(TAG_IMAGE);
                    String stringSrc = "";
                    if (nList.getLength() != 0) {
                        Node anode = nList.item(0);
                        stringSrc = (anode.getAttributes()).getNamedItem(TAG_IMAGE_SRC).getNodeValue();
                    }
                    days.add(new Data(parser.getNode(TAG_TITLE, eElement), parser.getNode(TAG_PUBLICATION_DATE, eElement),
                            parser.getNode(TAG_AUTHOR, eElement), parser.getNode(TAG_LINK, eElement), stringSrc));
                }
            }
        } catch (Exception e) {
            Toast toast = Toast.makeText(context,
                    e.getMessage(), Toast.LENGTH_LONG);
            toast.show();
        }

        mArrayAdapter = new DataAdapter(context, days);
        list.setAdapter(mArrayAdapter);


        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {

                Data entry = (Data) parent.getItemAtPosition(position);
                Intent intent = new Intent(context, WebViewInfo.class);
                intent.putExtra(KEY_VALUE, entry.getLink());
                context.startActivity(intent);
            }
        });

        pDialog.dismiss();
    }


}