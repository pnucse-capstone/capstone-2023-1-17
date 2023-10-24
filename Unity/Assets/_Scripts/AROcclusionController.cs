using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.XR.ARFoundation;
using UnityEngine.XR.ARSubsystems;
using TMPro;

public class AROcclusionController : MonoBehaviour
{
    [SerializeField]
    private Button envDepthBtn;
    private TextMeshProUGUI buttonText;

    private AROcclusionManager arOcclusionManager;

    private void Awake() {
        arOcclusionManager = GetComponent<AROcclusionManager>();

        envDepthBtn.onClick.AddListener(ChangeEnvDepthMode);
        UpdateEnvDepthBtn();    

    }

    private void ChangeEnvDepthMode() {
        int MAX = System.Enum.GetValues(typeof(EnvironmentDepthMode)).Length;
        if((int) arOcclusionManager.requestedEnvironmentDepthMode >= MAX - 1)
        {
            arOcclusionManager.requestedEnvironmentDepthMode = 0;
        }
        else {
            arOcclusionManager.requestedEnvironmentDepthMode++;
        }
        UpdateEnvDepthBtn();
    }

    private void UpdateEnvDepthBtn() {
        buttonText = envDepthBtn.GetComponentInChildren<TextMeshProUGUI>();
        buttonText.text = "Env:\n" + arOcclusionManager.requestedEnvironmentDepthMode.ToString();
        Debug.Log("TexT: " + buttonText.text);
    }

}
