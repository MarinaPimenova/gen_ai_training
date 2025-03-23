## 📑 Task

### Use the tasks project from module 01
For this task and for each subsequent task, use the results from the previous task as a basis for new changes.

### Implement more complex code:
1. Initialize PromptExecutionSettings and fill it with some settings, temperature for example.

2. Try to play with the temperature value, what was changed?

3. Get ChatCompletionService and initiate ChatHistory for storing all user and system messages to ChatHistory.

4. Use ChatHistory to provide context about previous messages for a AI model.

Validate the result and print it to the console.

### Evaluation Criteria
1. Prompt Execution Settings Initialization
- Correctly initialized PromptExecutionSettings with the specified settings (20%)
- Demonstrated understanding of the impact of changing the temperature value (10%)

2. ChatCompletionService and ChatHistory Initialization
- Correctly initialized ChatCompletionService with the appropriate model and client (15%)
- Properly set up ChatHistory to store user and system messages (15%)

3. Function Setup for Chatting with OpenAI
- Correctly implemented the function to interact with OpenAI based on ChatHistory (20%)
- Validated the result and printed it to the console (10%)

4. Functionality Testing
- Application runs without errors (5%)
- Responses are generated correctly and are relevant to the prompts (5%)

## Notes
Temperature is a hyperparameter that we find in stochastic models to regulate the randomness <br/>
in a sampling process. The temperature parameter of an LLM regulates the amount of randomness, <br/>
leading to more diverse outputs; therefore, <br/>
it is often claimed to be the creativity parameter.

## Resources
[Getting started with Semantic Kernel](https://learn.microsoft.com/en-us/semantic-kernel/get-started/quick-start-guide?pivots=programming-language-java)
[Chat with prompts](https://github.com/microsoft/semantic-kernel-java/blob/main/samples/semantickernel-concepts/semantickernel-syntax-examples/src/main/java/com/microsoft/semantickernel/samples/syntaxexamples/chatcompletion/Example30_ChatWithPrompts.java)
[Model selection and temperature settings](https://learn.microsoft.com/en-us/ai-builder/prompt-modelsettings)
[Chat completion](https://learn.microsoft.com/en-us/semantic-kernel/concepts/ai-services/chat-completion/?source=recommendations&tabs=csharp-AzureOpenAI%2Cpython-AzureOpenAI%2Cjava-AzureOpenAI&pivots=programming-language-java)
[Function calling with chat completion](https://learn.microsoft.com/en-us/semantic-kernel/concepts/ai-services/chat-completion/function-calling/?source=recommendations&pivots=programming-language-java)
[Building a company knowledge base and searching for documents on LLM and RAG](https://habr.com/ru/companies/raft/articles/863888/)

[semantickernel-concepts](https://habr.com/ru/companies/raft/articles/863888/)


## Tasks
1. Configures OpenAI using DIAL key and endpoint.
2. Initializes PromptExecutionSettings and sets temperature.
3. Modifies temperature and observes its effect.
4. Uses ChatHistory to store user/system messages.
5. Uses ChatCompletionService to provide context for AI responses.
6. Validates the result and prints it to the console.

