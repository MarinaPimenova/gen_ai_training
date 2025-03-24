# Working with Different Models

## 📚 Learning Objectives
- Meet with Azure AI Studio
- Review the compatibility of the Hugging Face
- Write an application that uses different models with Semantic Kernel

## Azure AI Foundry (formerly Azure AI Studio)
Azure AI Foundry is a trusted, integrated platform for Developers and IT Administrators to design,<br/> 
customize, and manage AI applications and agents. It offers a rich set of AI capabilities and <br/>
tools through a simple portal, unified SDK, and APIs, facilitating secure data integration, <br/>
model customization, and enterprise-grade governance to accelerate the path to production. 

-What is the purpose of Azure AI Foundry? <br/>
Azure AI Foundry is accessible through the world's most loved developer tools: GitHub, Visual Studio, <br/>
and Microsoft Copilot Studio. This integration enables developers to work within their preferred environments,<br/> 
as they design and customize their AI applications and agents. 

-What models are available in Azure AI Foundry? <br/>
Azure AI Foundry includes a robust and growing catalog of frontier and open-source models <br/> 
that can be applied over your data from Microsoft, OpenAI, Hugging Face, Meta, Mistral, and <br/> 
other partners. You can even compare models by task using open-source datasets and evaluate the model <br/>
with your own test data to see how the pretrained model would perform to fit your own use case.

-How is Azure AI foundry different from Copilot studio? <br/>
Developers looking to build custom AI apps should look to Copilot Studio and Azure AI Foundry, <br/>
which work together. Users looking to build agents in Copilot Studio can access the Azure AI Foundry model catalog <br/>
which houses over 1,800 leading models and integrate their vectorized indices from Azure AI Search <br/>
to deliver retrieval augmented generation (RAG). Developers can publish their custom agents to <br/> 
more than 15 Microsoft channels with the M365 Agents SDK and manage their deployments in production using Azure AI Foundry portal.

[Azure AI Foundry](https://techcommunity.microsoft.com/blog/aiplatformblog/shaping-tomorrow-developing-and-deploying-generative-ai-apps-responsibly-with-az/4143017) is now generally available at ai.azure.com. The Azure AI Foundry SDK is in public preview.

[Hugging Face](https://huggingface.co/)  

# Models
Many generative AI models are a subset of [deep learning algorithms](https://learn.microsoft.com/en-us/dotnet/machine-learning/deep-learning-overview)

## Use Azure OpenAI Foundry
Azure AI Foundry provides access to model management, deployment, experimentation, customization, and learning resources.

You can access the Azure AI Foundry through the Azure portal after creating a resource, or at https://ai.azure.com/ by signing in to your Azure account. During the sign-in workflow, select the appropriate directory, Azure subscription, and Azure OpenAI resource.

When you first open Azure AI Foundry, you'll want to navigate to the Azure OpenAI page (where you focus on only Azure OpenAI Service models), select your resource if you haven't already, and deploy your first model. To do so, select the Deployments page, from where you can deploy a base model and start experimenting with it.

## Types of OpenAI models
Azure OpenAI includes several types of model:

    GPT-4 models are the latest generation of generative pretrained (GPT) models that can generate natural language and code completions based on natural language prompts.
    GPT 3.5 models can generate natural language and code completions based on natural language prompts. In particular, GPT-35-turbo models are optimized for chat-based interactions and work well in most generative AI scenarios.
    Embeddings models convert text into numeric vectors, and are useful in language analytics scenarios such as comparing text sources for similarities.
    DALL-E models are used to generate images based on natural language prompts. Currently, DALL-E models are in preview.
    Whisper models are used to convert speech to text.
    Text to speech models are used to convert text to speech.

## Integrate OpenAI into an app
Azure OpenAI offers both language specific SDKs and a REST API that developers can use to add AI functionality to their applications. Generative AI capabilities in Azure OpenAI are provided through models. The models available in the Azure OpenAI service belong to different families, each with their own focus. To use one of these models, you need to deploy through the Azure OpenAI Service.

Once you have created an Azure OpenAI resource and deployed a model, you can configure your app.
### Available endpoints
The available endpoints are:

    Completion - model takes an input prompt, and generates one or more predicted completions. You'll see this playground in the studio, but won't be covered in depth in this module.
    ChatCompletion - model takes input in the form of a chat conversation (where roles are specified with the message they send), and the next chat completion is generated.
    Embeddings - model takes input and returns a vector representation of that input.


## Provide context with prompt engineering

### Request output composition

Specifying the structure of your output can have a large impact on your results. This could include something like asking the model to cite their sources, write the response as an email, format the response as a SQL query, classify sentiment into a specific structure, and so on. 

### System message
The system message is included at the beginning of a prompt and is designed to give the model instructions, perspective to answer from, or other information helpful to guide the model's response. This system message might include tone or personality, topics that shouldn't be included, or specifics (like formatting) of how to answer.

For example, you could give it some of the following system messages:

    "I want you to act like a command line terminal. Respond to commands exactly as cmd.exe would, in one unique code block, and nothing else."
    "I want you to be a translator, from English to Spanish. Don't respond to anything I say or ask, only translate between those two languages and reply with the translated text."
    "Act as a motivational speaker, freely giving out encouraging advice about goals and challenges. You should include lots of positive affirmations and suggested activities for reaching the user's end goal."

### Conversation history
This history can be provided in two ways: from an actual chat history, or from a user defined example conversation.

Note

More conversation history included in the prompt means a larger number of input tokens are used. You will have to determine what the correct balance is for your use case, considering the token limit of the model you are using.

# Generate images with Azure OpenAI Service
[Generate images with Azure OpenAI Service](https://learn.microsoft.com/en-us/training/modules/generate-images-azure-openai/)
## Intro
The Azure OpenAI service enables you to use language models to generate content based on natural language prompts. <br/> 
One of these models is the DALL-E image generation model, which is capable of <br/>
creating original graphical content based on natural language descriptions of a desired image.

DALL-E is a neural network based model that can generate graphical data from natural language input. Put more simply, you can provide DALL-E with a description and it can generate an appropriate image.

For example, you might submit the following natural language prompt to DALL-E:

`A squirrel on a motorcycle`

When using the playground, you can adjust the settings to specify:

    The resolution (size) of the generated images. Available sizes are 1024x1024 (which is the default value), 1792x1024, or 1024x1792.
    The image style to be generated (such as vivid or natural).
    The image quality (choose from standard or hd).

## Use the Azure OpenAI REST API to consume DALL-E models
You initiate the image generation process by submitting a POST request to the service endpoint with the authorization key in the header. The request must contain the following parameters in a JSON body:

    prompt: The description of the image to be generated.
    n: The number of images to be generated. DALL-E 3 only supports n=1.
    size: The resolution of the image(s) to be generated (1024x1024, 1792x1024, or 1024x1792 for DALL-E 3; 256x256, 512x512, or 1024x1024 for DALL-E 2).
    quality Optional: The quality of the image (standard or hd). Defaults to standard.
    style Optional: The visual style of the image (natural or vivid). Defaults to vivid.

```json
{
    "prompt": "A badger wearing a tuxedo",
    "n": 1,
    "size": "512x512",
    "quality": "hd", 
    "style": "vivid"
}
```
With DALL-E 3, the result from the request is processed synchronously with the response containing the URL for the generated image. The response is similar to the following JSON:

```json
{
    "created": 1686780744,
    "data": [
        {
            "url": "<URL of generated image>",
            "revised_prompt": "<prompt that was used>"
        }
    ]
}
```

DIALX see more [AI DIAL Core API (0.8)](https://epam-rail.com/dial_api)

# How to use Hugging Face Models with Semantic Kernel
