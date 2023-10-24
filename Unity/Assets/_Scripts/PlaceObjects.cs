using System.Collections.Generic;
using UnityEngine;
using UnityEngine.XR.ARFoundation;
using UnityEngine.XR.ARSubsystems;
using UnityEngine.EventSystems;
using UnityEngine.UI;
using TMPro;

public class PlaceObjects : MonoBehaviour
{
    private GameObject prefab;

    private ARRaycastManager aRRaycastManager;
    private List<ARRaycastHit> hits = new List<ARRaycastHit>();
    private GameObject arObject;

    [SerializeField]
    private Button Xbtn_plus;
    [SerializeField]
    private Button Ybtn_plus;
    [SerializeField]
    private Button Zbtn_plus;
    [SerializeField]
    private Button Xbtn_minus;
    [SerializeField]
    private Button Ybtn_minus;
    [SerializeField]
    private Button Zbtn_minus;

    [SerializeField]
    private Button scaleUpBtn;   // 스케일 증가 버튼
    [SerializeField]
    private Button scaleDownBtn; // 스케일 감소 버튼

    private Vector3 rotationAxis = Vector3.zero;
    public float rotationSpeed = 50f;
    private bool isUIPressed = false;

    [SerializeField]
    public TMP_Text textMesh;

    private void Awake()
    {
        aRRaycastManager = GetComponent<ARRaycastManager>();

        // 회전 버튼 이벤트
        AddPointerDownEvent(Xbtn_plus, () => { SetRotationAxis(Vector3.right); isUIPressed = true; });
        AddPointerDownEvent(Ybtn_plus, () => { SetRotationAxis(Vector3.up); isUIPressed = true; });
        AddPointerDownEvent(Zbtn_plus, () => { SetRotationAxis(Vector3.forward); isUIPressed = true; });
        AddPointerUpEvent(Xbtn_plus, () => { StopRotation(); isUIPressed = false; });
        AddPointerUpEvent(Ybtn_plus, () => { StopRotation(); isUIPressed = false; });
        AddPointerUpEvent(Zbtn_plus, () => { StopRotation(); isUIPressed = false; });

        AddPointerDownEvent(Xbtn_minus, () => { SetRotationAxis(Vector3.left); isUIPressed = true; });
        AddPointerDownEvent(Ybtn_minus, () => { SetRotationAxis(Vector3.down); isUIPressed = true; });
        AddPointerDownEvent(Zbtn_minus, () => { SetRotationAxis(Vector3.back); isUIPressed = true; });
        AddPointerUpEvent(Xbtn_minus, () => { StopRotation(); isUIPressed = false; });
        AddPointerUpEvent(Ybtn_minus, () => { StopRotation(); isUIPressed = false; });
        AddPointerUpEvent(Zbtn_minus, () => { StopRotation(); isUIPressed = false; });

        // 스케일 버튼 이벤트
        scaleUpBtn.onClick.AddListener(ScaleUp);
        scaleDownBtn.onClick.AddListener(ScaleDown);

    }

    void Update()
    {
        Connection connection = gameObject.GetComponent<Connection>();

        prefab = connection.LoadedObj;

        if (isUIPressed) 
        {
            arObject?.transform.Rotate(rotationAxis, rotationSpeed * Time.deltaTime, Space.World);
            return;
        }

        if (Input.touchCount > 0)
        {
            Touch touch = Input.GetTouch(0);

            // UI를 터치했다면 오브젝트 배치를 건너뜀
            if (EventSystem.current.IsPointerOverGameObject(touch.fingerId) || touch.phase == TouchPhase.Ended)
            {
                return;
            }

            if (aRRaycastManager.Raycast(touch.position, hits, TrackableType.PlaneWithinPolygon))
            {
                var hitPose = hits[0].pose;

                if (!arObject)
                {
                    prefab.transform.position = new Vector3(0, 0, 0);

                    prefab.transform.localScale = new Vector3(0.3f, 0.3f, 0.3f);

                    Debug.LogError("arobj made");
                    arObject = Instantiate(prefab, hitPose.position, hitPose.rotation);
                    Vector3 scale = arObject.transform.localScale;
                    textMesh.text = $"Scale: {scale.x}";
                }

                arObject.transform.position = hitPose.position;
            }
        }
    }

    void SetRotationAxis(Vector3 axis)
    {
        rotationAxis = axis;
    }

    void StopRotation()
    {
        rotationAxis = Vector3.zero;
    }

    // 크기를 10cm 증가시키는 메서드
    public void ScaleUp()
    {
        if (arObject != null)
        {
            Vector3 scaleChange = new Vector3(0.03f, 0.03f, 0.03f);  // 10cm 증가
            arObject.transform.localScale += scaleChange;

            Vector3 scale = arObject.transform.localScale;
            textMesh.text = $"Scale: {scale.x}";
        }
    }

    // 크기를 10cm 감소시키는 메서드
    public void ScaleDown()
    {
        if (arObject != null)
        {
            Vector3 scaleChange = new Vector3(0.03f, 0.03f, 0.03f);  // 10cm 감소
            arObject.transform.localScale -= scaleChange;

            Vector3 scale = arObject.transform.localScale;
            textMesh.text = $"Scale: {scale.x}";
        }
    }

    private void AddPointerDownEvent(Button button, UnityEngine.Events.UnityAction action)
    {
        EventTrigger trigger = button.gameObject.AddComponent<EventTrigger>();
        EventTrigger.Entry entry = new EventTrigger.Entry();
        entry.eventID = EventTriggerType.PointerDown;
        entry.callback.AddListener((eventData) => action());
        trigger.triggers.Add(entry);
    }

    private void AddPointerUpEvent(Button button, UnityEngine.Events.UnityAction action)
    {
        EventTrigger trigger = button.GetComponent<EventTrigger>() ?? button.gameObject.AddComponent<EventTrigger>();
        EventTrigger.Entry entry = new EventTrigger.Entry();
        entry.eventID = EventTriggerType.PointerUp;
        entry.callback.AddListener((eventData) => action());
        trigger.triggers.Add(entry);
    }
}
