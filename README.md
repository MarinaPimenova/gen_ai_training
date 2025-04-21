# Generative AI and Large Language Models
## Task#1
1. Set Up Project Environment
2. Obtain DIAL Key
3. Configuring the Application
4. Implement Application Logic 
5. Final Integration and Submission 
6. Free Practice: Explore and Innovate

## Tasks#2
1. Configures OpenAI using DIAL key and endpoint.
2. Initializes PromptExecutionSettings and sets temperature.
3. Modifies temperature and observes its effect.
4. Uses ChatHistory to store user/system messages.
5. Uses ChatCompletionService to provide context for AI responses.
6. Validates the result and prints it to the console.

## Task#3 - Working with Different Models
1. Configuration for Different Models
```shell
curl  -v 'https://ai-proxy.lab.epam.com/openai/deployments' \
-H "Api-Key: $MY_KEY"
```
2. Correctly modified the application.properties file to change the deployment name for different models <br/>
Successfully retrieved deployment names using the provided API. <br/>


3. Implementation of Model Switching <br/>
-Correctly implemented the logic to switch between different models based on the deployment name <br/>
-Ensured that the application can call different models from the Dial service <br/>


4. Comparison of Results <br/>
-Compared results for the same prompts using different models <br/>
-Compared results using different PromptExecutionSettings <br/>
-Documented observations and differences in the results <br/>
5. Validation <br/>
```json
[
  [
    {
      "authorRole": "assistant",
      "content": "What a lovely topic! Here's a list of famous artists who painted beautiful flowers in a vase:\n\n1. **Willem Kalf** (Dutch, 1619-1693): A master of Dutch Golden Age painting, Kalf created stunning still lifes with intricate flower arrangements in ornate vases.\n2. **Jan Davidsz. de Heem** (Dutch, 1606-1684): Another prominent Dutch painter, de Heem's works often featured lavish flower arrangements in decorative vases.\n3. **Rachel Ruysch** (Dutch, 1664-1750): As one of the few female painters of her time, Ruysch gained fame for her exquisite flower still lifes, frequently depicting blooms in elegant vases.\n4. **Jean-Honoré Fragonard** (French, 1732-1806): This Rococo painter's delicate, dreamy style is exemplified in his flower-in-a-vase works, often showcasing soft, pastel hues.\n5. **Pierre-Auguste Renoir** (French, 1841-1919): While primarily known for his Impressionist portraits and landscapes, Renoir also created beautiful, intimate paintings of flowers in vases.\n6. **Claude Monet** (French, 1840-1926): Monet's Impressionist series of water lilies (Nymphéas) often featured flowers in vases, capturing the play of light on water and petals.\n7. **Vincent van Gogh** (Dutch, 1853-1890): During his time in Arles, Van Gogh painted a series of vibrant flower still lifes, including \"Sunflowers\" and \"Almond Blossom,\" which frequently featured flowers in vases.\n8. **Édouard Manet** (French, 1832-1883): A pioneer of Impressionism, Manet's \"Still Life with Flowers in a Crystal Vase\" (1881) showcases his ability to capture the beauty of flowers in a vase.\n9. **Gustave Caillebotte** (French, 1848-1894): An Impressionist painter and collector, Caillebotte's \"Flowers in a Vase\" (1885) demonstrates his attention to detail and love for botanical subjects.\n10. **Henri Matisse** (French, 1869-1954): In his later",
      "contentType": "text",
      "modelId": "rlab-llama-large-Instruct"
    }
  ],
  [
    {
      "authorRole": "assistant",
      "content": "",
      "contentType": "text",
      "modelId": "dall-e-3"
    }
  ],
  [
    {
      "authorRole": "assistant",
      "content": "### Kernel Function Description\nA **Kernel Function** is a mathematical function used to map the original data into a higher-dimensional space where it may be easier to perform tasks like classification and clustering. It is primarily used in machine learning algorithms, particularly in Support Vector Machines (SVM). These functions measure similarity or a dot product in the transformed space without explicitly transforming data into that space, known as the \"kernel trick.\"\n\n### Example of Kernel Function Usage with Semantic Kernel Library\nAlthough the Semantic Kernel library directly doesn't exist as a well-known library, kernel functions are often used with support vector machines in libraries like scikit-learn. Let's look at a simple example using Python with scikit-learn to demonstrate the usage of an RBF (Radial Basis Function) kernel in SVM, which is conceptually similar and can give you an idea of how kernel functions operate:\n\n```python\nfrom sklearn.svm import SVC\nfrom sklearn.datasets import make_circles\nfrom sklearn.model_selection import train_test_split\nfrom sklearn.metrics import accuracy_score\n\n# Generate synthetic data\nX, y = make_circles(n_samples=100, factor=0.1, noise=0.1)\n\n# Split data into training and test sets\nX_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=42)\n\n# Create a SVM Classifier with RBF kernel\nclf = SVC(kernel='rbf')\n\n# Train the model using the training sets\nclf.fit(X_train, y_train)\n\n# Predict the response for test dataset\ny_pred = clf.predict(X_test)\n\n# Evaluate accuracy\naccuracy = accuracy_score(y_test, y_pred)\nprint(f\"Accuracy: {accuracy*100:.2f}%\")\n```\n\n### Explanation:\nIn this example:\n- **Data Generation**: `make_circles` is used for generating a simple binary classification dataset that has a circular decision boundary.\n- **SVM with RBF Kernel**: `SVC(kernel='rbf')` initializes a Support Vector Classifier using the Radial Basis Function kernel, suitable for the non-linear data pattern.\n- **Training and Prediction**: The model is trained on the training set and used to predict labels for the test set.\n- **Accuracy Assessment**: Performance of the classifier is evaluated through accuracy.\n\nThis example, while using Python's scikit-learn, provides a conceptual take on how kernel functions facilitate handling data that is not linearly separable",
      "contentType": "text",
      "modelId": "gpt-4-turbo"
    }
  ]
]
``` 
## Task#4 - Semantic Kernel Plugins
- Implement code that uses different types of SK plugins

### Call function from custom plugin:
- Use [this](https://devblogs.microsoft.com/semantic-kernel/using-semantic-kernel-to-create-a-time-plugin-with-java/) is an example or you can follow [Microsoft documentation](https://learn.microsoft.com/en-us/semantic-kernel/concepts/plugins/?pivots=programming-language-java)
- See the **SimplePlugin.java** usage in the **examples** folder of the course project

### Implement custom plugin
Your task is to create series of custom plugins the existing system.
This function should be called by a model based on user request, it can return some data <br/>
or make actions inside the application.
As ideas for plugins you can use:
Age calculator, Weather forecast, Currency converter, <br/>
Turn of the lamp (just change the flag of some boolean variable),
and so on, <br/>
in general, any plugin that can do some calculations or provide some information outside model knowledge.

Note: no need to implement the real 3-rd party service integration, the response could be simply mocked.
### Evaluation Criteria
2. Implementation and Integration several custom plugins
- Correctly implemented new function within the existing system 
- System is capable of calling correct plugin based on user input 
- System correctly generates and displays result using function based on user input 
