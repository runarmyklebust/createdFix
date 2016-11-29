package me.myklebust.createdfix;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.enonic.xp.content.ContentPropertyNames;
import com.enonic.xp.data.PropertyPath;
import com.enonic.xp.node.NodeId;
import com.enonic.xp.node.NodeIds;
import com.enonic.xp.node.NodeService;
import com.enonic.xp.node.UpdateNodeParams;
import com.enonic.xp.script.bean.BeanContext;
import com.enonic.xp.script.bean.ScriptBean;

public class CreatedFixBean
    implements ScriptBean
{
    private NodeService nodeService;

    private final static PropertyPath CREATED_TIME_PROPERTY_PATH = PropertyPath.from( ContentPropertyNames.CREATED_TIME );

    private final static Logger LOG = LoggerFactory.getLogger( CreatedFixBean.class );

    @SuppressWarnings("unused")
    public int setCreated( final String[] ids, final String created )
    {
        return doSetCreated( NodeIds.from( ids ), Instant.parse( created ) );
    }

    @SuppressWarnings("unused")
    public int setCreated( final String id, final String created )
    {
        return doSetCreated( NodeIds.from( id ), Instant.parse( created ) );
    }

    private int doSetCreated( final NodeIds nodeIds, final Instant created )
    {
        int updated = 0;

        for ( final NodeId nodeId : nodeIds )
        {
            this.nodeService.update( UpdateNodeParams.create().
                id( nodeId ).
                editor( ( node ) -> {
                    node.data.setInstant( CREATED_TIME_PROPERTY_PATH, created );
                } ).
                build() );

            LOG.info( "Updated node with id [" + nodeId + "] with createdTime [" + created + "]" );
            updated++;
        }

        return updated;
    }

    @Override
    public void initialize( final BeanContext context )
    {
        this.nodeService = context.getService( NodeService.class ).get();
    }
}
