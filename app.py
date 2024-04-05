### loading the .env file where the key is present
##load_dotenv is present inside my main.py file in library so i imported like this. 
from dotenv import main
main.load_dotenv()


##importing necessasy libraries
import os
from PIL import Image
import google.generativeai as genai
from gtts import gTTS
from io import BytesIO
import cv2
import whisper
from tempfile import NamedTemporaryFile
from streamlit_mic_recorder import speech_to_text
import streamlit as st
import openai
from st_audiorec import st_audiorec
import pyttsx3 
import speech_recognition as sr
import wave         
from pydub import AudioSegment


##importing the model
genai.configure(api_key=os.getenv("GOOGLE"))
# openai.api_key = os.getenv("OPENAI")
model = genai.GenerativeModel('gemini-pro-vision')

##function to get the response from the model
def get_data(input,image,prompt):
    response=model.generate_content([input,image[0],prompt])
    return response.text



def image_process(uploaded_file):
    if uploaded_file is not None:

        bytes_date=uploaded_file.getvalue()
        image_parts =[
            {
                "mime_type":uploaded_file.type,
                "data":bytes_date
            }
        ]         
        return image_parts
    else :
        raise FileNotFoundError("Check the file is uploade properly")
    
# def modelrespon(text):
#         respo=gpt_model.chat(text)
#         sound_file=BytesIO()
#         tts = gTTS(respo, lang='en')
#         tts.write_to_fp(sound_file)
#         st.audio(sound_file)



st.set_page_config(page_title="Image Information Extraction",page_icon="📑")



##getting input from the user
input="You are an image analysis expert with a specialization in assisting visually impaired users. Users will upload various images and pose questions related to the content. Your task is to provide answers based solely on the visual information within the image. Additionally, your system should be able to identify faces within the image and, if available, provide the names of individuals. The goal is to create an inclusive and informative experience for users with visual impairments, offering detailed insights and recognition capabilities directly from uploaded images."
uploaded_file=st.file_uploader("Choose an image....📑",type=["jpg","jpeg","png"])
image=""


if uploaded_file is not None:
    image=Image.open(uploaded_file)
    st.image(image,caption="Uploaded Image,use_column_width=True")
submit=st.button("RUN")


input_prompt="""
You are an expert in analysing the image.User will upload the any kind of image and will ask the questions 
about the images you need to answer the questions from the image alone and you can also find the faces in the image
and tell their name if you have the information and tell appromixate distance from the user point of veiw
"""


if submit:
    image_data=image_process(uploaded_file)
    reponse=get_data(input_prompt,image_data,input)
    st.subheader("The Details is")
    sound_file=BytesIO()
    tts = gTTS(reponse, lang='en')
    tts.write_to_fp(sound_file)
    st.audio(sound_file)

r = sr.Recognizer() 
audio_bytes = st_audiorec()
if audio_bytes:
    with wave.open('output.wav', 'wb') as wav_file:
         wav_file.setnchannels(1) # Mono
         wav_file.setsampwidth(2) # Sample width in bytes
         wav_file.setframerate(44100) # Sample rate
         wav_file.writeframes(audio_bytes)
    # mytext=r.recognize_google('greeting_voice.mp3')
    # st.markdown(mytext)
# text = speech_to_text(
#     language='en',
#     start_prompt="Start recording",
#     stop_prompt="Stop recording",
#     just_once=False,
#     use_container_width=False,
#     callback=None,
#     args=(),
#     kwargs={},
#     key=None
# )
# modelrespon(text)

#AIzaSyD399NdUmkzdpHUQQOjen-6lbbaqSvMCr0