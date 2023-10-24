using Dummiesman;
using System.Collections;
using System.IO;
using System.Text;
using UnityEngine;
using UnityEngine.Networking;
using UnityEngine.UI;

public class Connection : MonoBehaviour
{
    private string URL = "http://172.22.61.33:3389";
    
    private string objUrl;
    private string mtlUrl;
    private string textureUrl;
    private GameObject loadedObj;

    public string id = "1";

    [SerializeField]
    private Button ConnectBtn;

    public GameObject LoadedObj 
    {
    get { return loadedObj; }
    }

    private void Awake()
    {
        ConnectBtn.onClick.AddListener(() => StartCoroutine(LoadModel()));
    }

    public void ReceiveMessage(string message)
    {
        id = message;
        Debug.Log("receive userId: " + id + ".");
    }

    IEnumerator LoadModel()
    {
        Debug.Log("call userId: " + id + ".");
        objUrl = URL + "/obj?id=" + id;
        mtlUrl = URL + "/mtl?id=" + id;
        textureUrl = URL + "/png?id=" + id;
        
        UnityWebRequest objRequest = UnityWebRequest.Get(objUrl);
        yield return objRequest.SendWebRequest();
        if (objRequest.result != UnityWebRequest.Result.Success)
        {
            Debug.LogError("Failed to load OBJ file");
            yield break;
        }
        string objText = objRequest.downloadHandler.text;

        // Load .mtl file
        UnityWebRequest mtlRequest = UnityWebRequest.Get(mtlUrl);
        yield return mtlRequest.SendWebRequest();
        if (mtlRequest.result != UnityWebRequest.Result.Success)
        {
            Debug.LogError("Failed to load MTL file");
            yield break;
        }
        string mtlText = mtlRequest.downloadHandler.text;

        // Load .png texture
        UnityWebRequest textureRequest = UnityWebRequestTexture.GetTexture(textureUrl);
        yield return textureRequest.SendWebRequest();
        if (textureRequest.result != UnityWebRequest.Result.Success)
        {
            Debug.LogError("Failed to load texture");
            yield break;
        }
        Texture2D texture = ((DownloadHandlerTexture)textureRequest.downloadHandler).texture;

        // Use Dummiesman to create a GameObject from the .obj and .mtl text
        var objStream = new MemoryStream(Encoding.UTF8.GetBytes(objText));
        var mtlStream = new MemoryStream(Encoding.UTF8.GetBytes(mtlText));
        loadedObj = new OBJLoader().Load(objStream, mtlStream);

        // Apply the texture to the GameObject's materials
        if (loadedObj.GetComponent<Renderer>() != null)
        {
            loadedObj.GetComponent<Renderer>().material.mainTexture = texture;
        }
        else if (loadedObj.transform.childCount > 0) 
        {
            foreach (Transform child in loadedObj.transform)
            {
                var renderer = child.GetComponent<Renderer>();
                if (renderer != null)
                {
                    renderer.material.mainTexture = texture;
                }
            }
        }
        Debug.Log("Model loaded successfully");
    }
}
