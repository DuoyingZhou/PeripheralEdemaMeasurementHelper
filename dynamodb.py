import json,time,sys
import thread
from collections import OrderedDict
import threading
from threading import Thread
from botocore.exceptions import ClientError
import boto3, boto
#from boto3.dynamodb.conditions import Key,Attr
from boto.dynamodb2.fields import HashKey, RangeKey
import boto.dynamodb2
sys.path.append('../utils')
import tripupdate,vehicle,alert,mtaUpdates,aws
import datetime
# AWS ID
ACCOUNT_ID = ''
IDENTITY_POOL_ID = ''
ROLE_ARN = ''

# Use cognito to get an identity.
cognito = boto.connect_cognito_identity()
cognito_id = cognito.get_id(ACCOUNT_ID, IDENTITY_POOL_ID)
oidc = cognito.get_open_id_token(cognito_id['IdentityId'])

# Further setup your STS using the code below
sts = boto.connect_sts()
assumedRoleObject = sts.assume_role_with_web_identity(ROLE_ARN, "XX", oidc['Token'])

# table
DYNAMODB_TABLE_NAME = 'USER1'

# DynamoDB
client_dynamo = boto.dynamodb2.connect_to_region(
        'us-east-1',
        aws_access_key_id=assumedRoleObject.credentials.access_key,
        aws_secret_access_key=assumedRoleObject.credentials.secret_key,
        security_token=assumedRoleObject.credentials.session_token)
from boto.dynamodb2.table import Table
table_dynamo = Table(DYNAMODB_TABLE_NAME, connection=client_dynamo)
try:
    MTA = Table.create('USER1', schema=[HashKey('TIME')], connection=client_dynamo)
    time.sleep(30)
except:
    MTA = Table('USER1', connection=client_dynamo)

### YOUR CODE HERE ####
def putdata(dentduration, stage):
	USER1.put_item(data={
       'TIME' : str(time.time()),
	   'DENTDURATION': str(dentduration),
	   'STAGE': str(stage),
            })
        