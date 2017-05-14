from watchdog.observers import Observer
from watchdog.events import FileSystemEventHandler
from predict import predict
from dynamodb import putdata

class ExampleHandler(FileSystemEventHandler):
    def on_created(self, event): # when file is created
        # do something, eg. call your function to process the image
		dentduration, stage = predict(event.src_path)
		putdata (dentduration, stage)
        print "Got event for file %s" % event.src_path 

observer = Observer()
event_handler = ExampleHandler() # create event handler
# set observer to use created handler in directory
observer.schedule(event_handler, path='/folder/to/watch')
observer.start()

# sleep until keyboard interrupt, then stop + rejoin the observer
try:
    while True:
        time.sleep(1)
except KeyboardInterrupt:
    observer.stop()

observer.join()